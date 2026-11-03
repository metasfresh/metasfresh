-- Run mode: SWING_CLIENT

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> ETD
-- Column: M_ShipperTransportation.ETD
-- 2025-10-02T16:20:11.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591245,754550,0,540096,0,TO_TIMESTAMP('2025-10-02 16:20:09.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ETD',0,0,210,0,1,1,TO_TIMESTAMP('2025-10-02 16:20:09.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:20:11.438Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:20:11.543Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584066)
;

-- 2025-10-02T16:20:11.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754550
;

-- 2025-10-02T16:20:11.709Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754550)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> ETA
-- Column: M_ShipperTransportation.ETA
-- 2025-10-02T16:20:42.873Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591246,754551,0,540096,0,TO_TIMESTAMP('2025-10-02 16:20:41.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ETA',0,0,220,0,1,1,TO_TIMESTAMP('2025-10-02 16:20:41.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:20:42.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754551 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:20:43.028Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584067)
;

-- 2025-10-02T16:20:43.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754551
;

-- 2025-10-02T16:20:43.186Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754551)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> ATD
-- Column: M_ShipperTransportation.ATD
-- 2025-10-02T16:21:13.693Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591247,754552,0,540096,0,TO_TIMESTAMP('2025-10-02 16:21:12.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ATD',0,0,230,0,1,1,TO_TIMESTAMP('2025-10-02 16:21:12.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:21:13.770Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754552 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:21:13.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584068)
;

-- 2025-10-02T16:21:13.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754552
;

-- 2025-10-02T16:21:14.002Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754552)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> ATA
-- Column: M_ShipperTransportation.ATA
-- 2025-10-02T16:21:42.752Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591248,754553,0,540096,0,TO_TIMESTAMP('2025-10-02 16:21:41.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ATA',0,0,240,0,1,1,TO_TIMESTAMP('2025-10-02 16:21:41.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:21:42.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754553 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:21:42.908Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584069)
;

-- 2025-10-02T16:21:42.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754553
;

-- 2025-10-02T16:21:43.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754553)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> CRD
-- Column: M_ShipperTransportation.CRD
-- 2025-10-02T16:22:12.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591249,754554,0,540096,0,TO_TIMESTAMP('2025-10-02 16:22:11.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewünschte Lieferbereitschaft beim Lieferant',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'CRD',0,0,250,0,1,1,TO_TIMESTAMP('2025-10-02 16:22:11.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:22:12.682Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754554 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:22:12.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584070)
;

-- 2025-10-02T16:22:12.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754554
;

-- 2025-10-02T16:22:12.917Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754554)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> Buchung bestätigt
-- Column: M_ShipperTransportation.IsBookingConfirmed
-- 2025-10-02T16:35:35.747Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591250,754555,0,540096,0,TO_TIMESTAMP('2025-10-02 16:35:34.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Verschiffungsbuchung bestätigt?',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Buchung bestätigt',0,0,260,0,1,1,TO_TIMESTAMP('2025-10-02 16:35:34.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:35:35.826Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754555 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:35:35.905Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584071)
;

-- 2025-10-02T16:35:35.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754555
;

-- 2025-10-02T16:35:36.063Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754555)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> Container-Nr.
-- Column: M_ShipperTransportation.ContainerNo
-- 2025-10-02T16:36:25.834Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591251,754556,0,540096,0,TO_TIMESTAMP('2025-10-02 16:36:24.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Container-Nr.',0,0,270,0,1,1,TO_TIMESTAMP('2025-10-02 16:36:24.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:36:25.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754556 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:36:25.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584072)
;

-- 2025-10-02T16:36:26.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754556
;

-- 2025-10-02T16:36:26.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754556)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> Tracking-Nr.
-- Column: M_ShipperTransportation.TrackingID
-- 2025-10-02T16:37:00.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591252,754557,0,540096,0,TO_TIMESTAMP('2025-10-02 16:36:58.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tracking-ID der Sendung',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Tracking-Nr.',0,0,280,0,1,1,TO_TIMESTAMP('2025-10-02 16:36:58.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:37:00.485Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754557 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:37:00.564Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584073)
;

-- 2025-10-02T16:37:00.645Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754557
;

-- 2025-10-02T16:37:00.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754557)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> B/L erhalten
-- Column: M_ShipperTransportation.IsBLReceived
-- 2025-10-02T16:37:44.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591253,754558,0,540096,0,TO_TIMESTAMP('2025-10-02 16:37:43.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ist das Konnossement eingegangen?',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'B/L erhalten',0,0,290,0,1,1,TO_TIMESTAMP('2025-10-02 16:37:43.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:37:44.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754558 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:37:44.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584074)
;

-- 2025-10-02T16:37:44.828Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754558
;

-- 2025-10-02T16:37:44.906Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754558)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> B/L Datum
-- Column: M_ShipperTransportation.BLDate
-- 2025-10-02T16:38:15.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591254,754559,0,540096,0,TO_TIMESTAMP('2025-10-02 16:38:14.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'B/L Datum',0,0,300,0,1,1,TO_TIMESTAMP('2025-10-02 16:38:14.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:38:15.519Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:38:15.597Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584075)
;

-- 2025-10-02T16:38:15.677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754559
;

-- 2025-10-02T16:38:15.757Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754559)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> WE Avis
-- Column: M_ShipperTransportation.IsWENotice
-- 2025-10-02T16:38:48.113Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591255,754560,0,540096,0,TO_TIMESTAMP('2025-10-02 16:38:46.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Containerplanung abgeschlossen?',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'WE Avis',0,0,310,0,1,1,TO_TIMESTAMP('2025-10-02 16:38:46.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:38:48.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754560 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:38:48.270Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584076)
;

-- 2025-10-02T16:38:48.349Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754560
;

-- 2025-10-02T16:38:48.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754560)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> Vessel Name
-- Column: M_ShipperTransportation.VesselName
-- 2025-10-02T16:39:16.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591256,754561,0,540096,0,TO_TIMESTAMP('2025-10-02 16:39:15.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Vessel Name',0,0,320,0,1,1,TO_TIMESTAMP('2025-10-02 16:39:15.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:39:16.675Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754561 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:39:16.752Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584077)
;

-- 2025-10-02T16:39:16.832Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754561
;

-- 2025-10-02T16:39:16.911Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754561)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> POL
-- Column: M_ShipperTransportation.POL_ID
-- 2025-10-02T16:39:48.286Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591257,754562,0,540096,0,TO_TIMESTAMP('2025-10-02 16:39:46.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ladehafen',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'POL',0,0,330,0,1,1,TO_TIMESTAMP('2025-10-02 16:39:46.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:39:48.365Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:39:48.445Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584078)
;

-- 2025-10-02T16:39:48.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754562
;

-- 2025-10-02T16:39:48.607Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754562)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> POD
-- Column: M_ShipperTransportation.POD_ID
-- 2025-10-02T16:40:15.720Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591258,754563,0,540096,0,TO_TIMESTAMP('2025-10-02 16:40:14.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Entladehafen',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'POD',0,0,340,0,1,1,TO_TIMESTAMP('2025-10-02 16:40:14.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T16:40:15.797Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T16:40:15.875Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584079)
;

-- 2025-10-02T16:40:15.957Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754563
;

-- 2025-10-02T16:40:16.037Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754563)
;

-- UI Column: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10
-- UI Element Group: logistics
-- 2025-10-02T16:40:46.353Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540387,553587,TO_TIMESTAMP('2025-10-02 16:40:45.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','logistics',30,TO_TIMESTAMP('2025-10-02 16:40:45.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.ETD
-- Column: M_ShipperTransportation.ETD
-- 2025-10-02T16:41:19.882Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754550,0,540096,553587,637548,'F',TO_TIMESTAMP('2025-10-02 16:41:18.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum','Y','N','N','Y','N','N','N',0,'ETD',10,0,0,TO_TIMESTAMP('2025-10-02 16:41:18.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.ETA
-- Column: M_ShipperTransportation.ETA
-- 2025-10-02T16:41:42.673Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754551,0,540096,553587,637549,'F',TO_TIMESTAMP('2025-10-02 16:41:41.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum','Y','N','N','Y','N','N','N',0,'ETA',20,0,0,TO_TIMESTAMP('2025-10-02 16:41:41.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.ATD
-- Column: M_ShipperTransportation.ATD
-- 2025-10-02T16:41:59.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754552,0,540096,553587,637550,'F',TO_TIMESTAMP('2025-10-02 16:41:58.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum','Y','N','N','Y','N','N','N',0,'ATD',30,0,0,TO_TIMESTAMP('2025-10-02 16:41:58.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.ATA
-- Column: M_ShipperTransportation.ATA
-- 2025-10-02T16:42:16.517Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754553,0,540096,553587,637551,'F',TO_TIMESTAMP('2025-10-02 16:42:15.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum','Y','N','N','Y','N','N','N',0,'ATA',40,0,0,TO_TIMESTAMP('2025-10-02 16:42:15.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.CRD
-- Column: M_ShipperTransportation.CRD
-- 2025-10-02T16:42:37.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754554,0,540096,553587,637552,'F',TO_TIMESTAMP('2025-10-02 16:42:36.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewünschte Lieferbereitschaft beim Lieferant','Y','N','N','Y','N','N','N',0,'CRD',50,0,0,TO_TIMESTAMP('2025-10-02 16:42:36.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.Buchung bestätigt
-- Column: M_ShipperTransportation.IsBookingConfirmed
-- 2025-10-02T16:42:55.197Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754555,0,540096,553587,637553,'F',TO_TIMESTAMP('2025-10-02 16:42:54.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Verschiffungsbuchung bestätigt?','Y','N','N','Y','N','N','N',0,'Buchung bestätigt',60,0,0,TO_TIMESTAMP('2025-10-02 16:42:54.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.Container-Nr.
-- Column: M_ShipperTransportation.ContainerNo
-- 2025-10-02T16:43:12.649Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754556,0,540096,553587,637554,'F',TO_TIMESTAMP('2025-10-02 16:43:11.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers','Y','N','N','Y','N','N','N',0,'Container-Nr.',70,0,0,TO_TIMESTAMP('2025-10-02 16:43:11.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.Tracking-Nr.
-- Column: M_ShipperTransportation.TrackingID
-- 2025-10-02T16:43:29.526Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754557,0,540096,553587,637555,'F',TO_TIMESTAMP('2025-10-02 16:43:28.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tracking-ID der Sendung','Y','N','N','Y','N','N','N',0,'Tracking-Nr.',80,0,0,TO_TIMESTAMP('2025-10-02 16:43:28.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.B/L erhalten
-- Column: M_ShipperTransportation.IsBLReceived
-- 2025-10-02T16:43:45.437Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754558,0,540096,553587,637556,'F',TO_TIMESTAMP('2025-10-02 16:43:44.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ist das Konnossement eingegangen?','Y','N','N','Y','N','N','N',0,'B/L erhalten',90,0,0,TO_TIMESTAMP('2025-10-02 16:43:44.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.B/L Datum
-- Column: M_ShipperTransportation.BLDate
-- 2025-10-02T16:44:02.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754559,0,540096,553587,637557,'F',TO_TIMESTAMP('2025-10-02 16:44:01.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'B/L Datum',100,0,0,TO_TIMESTAMP('2025-10-02 16:44:01.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.WE Avis
-- Column: M_ShipperTransportation.IsWENotice
-- 2025-10-02T16:44:23.743Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754560,0,540096,553587,637558,'F',TO_TIMESTAMP('2025-10-02 16:44:22.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Containerplanung abgeschlossen?','Y','N','N','Y','N','N','N',0,'WE Avis',110,0,0,TO_TIMESTAMP('2025-10-02 16:44:22.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.Vessel Name
-- Column: M_ShipperTransportation.VesselName
-- 2025-10-02T16:44:43.024Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754561,0,540096,553587,637559,'F',TO_TIMESTAMP('2025-10-02 16:44:42.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Vessel Name',120,0,0,TO_TIMESTAMP('2025-10-02 16:44:42.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.POL
-- Column: M_ShipperTransportation.POL_ID
-- 2025-10-02T16:45:00.482Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754562,0,540096,553587,637560,'F',TO_TIMESTAMP('2025-10-02 16:44:59.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ladehafen','Y','N','N','Y','N','N','N',0,'POL',130,0,0,TO_TIMESTAMP('2025-10-02 16:44:59.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 10 -> logistics.POD
-- Column: M_ShipperTransportation.POD_ID
-- 2025-10-02T16:45:16.443Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754563,0,540096,553587,637561,'F',TO_TIMESTAMP('2025-10-02 16:45:15.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Entladehafen','Y','N','N','Y','N','N','N',0,'POD',140,0,0,TO_TIMESTAMP('2025-10-02 16:45:15.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

