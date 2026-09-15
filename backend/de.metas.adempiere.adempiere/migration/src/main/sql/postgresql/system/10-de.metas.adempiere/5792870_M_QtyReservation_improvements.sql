-- Run mode: SWING_CLIENT

-- Window: Qty Reservation, InternalName=null
-- 2026-03-07T22:37:56.430Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584594,0,542106,TO_TIMESTAMP('2026-03-07 22:37:55.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Qty Reservation','N',TO_TIMESTAMP('2026-03-07 22:37:55.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-03-07T22:37:56.444Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542106 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-03-07T22:37:56.640Z
/* DDL */  select update_window_translation_from_ad_element(584594)
;

-- 2026-03-07T22:37:56.663Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542106
;

-- 2026-03-07T22:37:56.667Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542106)
;

-- Tab: Mengenreservierung(542106,D) -> Qty Reservation
-- Table: M_QtyReservation
-- 2026-03-07T22:38:07.896Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584594,0,549081,542583,542106,'Y',TO_TIMESTAMP('2026-03-07 22:38:07.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_QtyReservation','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Qty Reservation','N',10,0,TO_TIMESTAMP('2026-03-07 22:38:07.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:07.902Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549081 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-03-07T22:38:07.905Z
/* DDL */  select update_tab_translation_from_ad_element(584594)
;

-- 2026-03-07T22:38:07.910Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549081)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Qty Reservation
-- Column: M_QtyReservation.M_QtyReservation_ID
-- 2026-03-07T22:38:18.387Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592094,774861,0,549081,TO_TIMESTAMP('2026-03-07 22:38:18.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Qty Reservation',TO_TIMESTAMP('2026-03-07 22:38:18.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:18.392Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:18.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584594)
;

-- 2026-03-07T22:38:18.399Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774861
;

-- 2026-03-07T22:38:18.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774861)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Mandant
-- Column: M_QtyReservation.AD_Client_ID
-- 2026-03-07T22:38:18.534Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592095,774862,0,549081,TO_TIMESTAMP('2026-03-07 22:38:18.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-03-07 22:38:18.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:18.536Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:18.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-03-07T22:38:19.254Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774862
;

-- 2026-03-07T22:38:19.255Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774862)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Sektion
-- Column: M_QtyReservation.AD_Org_ID
-- 2026-03-07T22:38:19.420Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592096,774863,0,549081,TO_TIMESTAMP('2026-03-07 22:38:19.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-03-07 22:38:19.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:19.423Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:19.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-03-07T22:38:19.622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774863
;

-- 2026-03-07T22:38:19.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774863)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Aktiv
-- Column: M_QtyReservation.IsActive
-- 2026-03-07T22:38:19.750Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592097,774864,0,549081,TO_TIMESTAMP('2026-03-07 22:38:19.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-03-07 22:38:19.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:19.752Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:19.756Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-03-07T22:38:20.007Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774864
;

-- 2026-03-07T22:38:20.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774864)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Auftragsposition
-- Column: M_QtyReservation.C_OrderLine_ID
-- 2026-03-07T22:38:20.127Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592102,774865,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'D','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2026-03-07 22:38:20.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.130Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561)
;

-- 2026-03-07T22:38:20.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774865
;

-- 2026-03-07T22:38:20.144Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774865)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Produkt
-- Column: M_QtyReservation.M_Product_ID
-- 2026-03-07T22:38:20.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592103,774866,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2026-03-07 22:38:20.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-03-07T22:38:20.318Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774866
;

-- 2026-03-07T22:38:20.319Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774866)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Lager
-- Column: M_QtyReservation.M_Warehouse_ID
-- 2026-03-07T22:38:20.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592104,774867,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2026-03-07 22:38:20.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.444Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2026-03-07T22:38:20.460Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774867
;

-- 2026-03-07T22:38:20.461Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774867)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Lieferant
-- Column: M_QtyReservation.C_BPartner_Vendor_ID
-- 2026-03-07T22:38:20.576Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592105,774868,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Lieferant',TO_TIMESTAMP('2026-03-07 22:38:20.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.582Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542647)
;

-- 2026-03-07T22:38:20.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774868
;

-- 2026-03-07T22:38:20.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774868)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Versorgungsart
-- Column: M_QtyReservation.SupplyType
-- 2026-03-07T22:38:20.714Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592106,774869,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2,'D','Y','N','N','N','N','N','N','N','Versorgungsart',TO_TIMESTAMP('2026-03-07 22:38:20.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.718Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584567)
;

-- 2026-03-07T22:38:20.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774869
;

-- 2026-03-07T22:38:20.727Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774869)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Zugesagter Termin
-- Column: M_QtyReservation.DatePromised
-- 2026-03-07T22:38:20.853Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592107,774870,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',29,'D','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','N','N','N','N','N','Zugesagter Termin',TO_TIMESTAMP('2026-03-07 22:38:20.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2026-03-07T22:38:20.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774870
;

-- 2026-03-07T22:38:20.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774870)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Maßeinheit
-- Column: M_QtyReservation.C_UOM_ID
-- 2026-03-07T22:38:20.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592108,774871,0,549081,TO_TIMESTAMP('2026-03-07 22:38:20.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2026-03-07 22:38:20.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:20.994Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:20.996Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-03-07T22:38:21.037Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774871
;

-- 2026-03-07T22:38:21.039Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774871)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> TU Anzahl
-- Column: M_QtyReservation.QtyTU
-- 2026-03-07T22:38:21.178Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592109,774872,0,549081,TO_TIMESTAMP('2026-03-07 22:38:21.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D','Y','N','N','N','N','N','N','N','TU Anzahl',TO_TIMESTAMP('2026-03-07 22:38:21.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:21.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:21.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542490)
;

-- 2026-03-07T22:38:21.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774872
;

-- 2026-03-07T22:38:21.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774872)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Menge
-- Column: M_QtyReservation.Qty
-- 2026-03-07T22:38:21.315Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592110,774873,0,549081,TO_TIMESTAMP('2026-03-07 22:38:21.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',14,'D','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2026-03-07 22:38:21.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:21.318Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:21.320Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2026-03-07T22:38:21.333Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774873
;

-- 2026-03-07T22:38:21.334Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774873)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> AttributesKey (technical)
-- Column: M_QtyReservation.AttributesKey
-- 2026-03-07T22:38:21.458Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592111,774874,0,549081,TO_TIMESTAMP('2026-03-07 22:38:21.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','N','N','N','N','N','N','N','AttributesKey (technical)',TO_TIMESTAMP('2026-03-07 22:38:21.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:21.461Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:21.463Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543666)
;

-- 2026-03-07T22:38:21.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774874
;

-- 2026-03-07T22:38:21.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774874)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Gelieferte Menge
-- Column: M_QtyReservation.QtyDelivered
-- 2026-03-07T22:38:21.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592130,774875,0,549081,TO_TIMESTAMP('2026-03-07 22:38:21.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gelieferte Menge',14,'D','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','N','N','N','N','N','Gelieferte Menge',TO_TIMESTAMP('2026-03-07 22:38:21.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:21.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:21.603Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(528)
;

-- 2026-03-07T22:38:21.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774875
;

-- 2026-03-07T22:38:21.613Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774875)
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Verarbeitet
-- Column: M_QtyReservation.Processed
-- 2026-03-07T22:38:21.735Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579379/*OLD=592131*/,774876,0,549081,TO_TIMESTAMP('2026-03-07 22:38:21.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2026-03-07 22:38:21.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:38:21.737Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:38:21.739Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2026-03-07T22:38:21.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774876
;

-- 2026-03-07T22:38:21.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774876)
;

-- Tab: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D)
-- UI Section: main
-- 2026-03-07T22:38:59.308Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549081,547600,TO_TIMESTAMP('2026-03-07 22:38:59.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-03-07 22:38:59.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-03-07T22:38:59.319Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547600 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main
-- UI Column: 10
-- 2026-03-07T22:39:02.744Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549277,547600,TO_TIMESTAMP('2026-03-07 22:39:02.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-03-07 22:39:02.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main
-- UI Column: 20
-- 2026-03-07T22:39:03.935Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549278,547600,TO_TIMESTAMP('2026-03-07 22:39:03.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-03-07 22:39:03.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10
-- UI Element Group: primary
-- 2026-03-07T22:39:15.059Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549277,554986,TO_TIMESTAMP('2026-03-07 22:39:14.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','primary',10,'primary',TO_TIMESTAMP('2026-03-07 22:39:14.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Auftragsposition
-- Column: M_QtyReservation.C_OrderLine_ID
-- 2026-03-07T22:39:46.277Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774865,0,549081,554986,648506,'F',TO_TIMESTAMP('2026-03-07 22:39:45.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','Auftragsposition',10,0,0,TO_TIMESTAMP('2026-03-07 22:39:45.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Lager
-- Column: M_QtyReservation.M_Warehouse_ID
-- 2026-03-07T22:39:56.886Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774867,0,549081,554986,648507,'F',TO_TIMESTAMP('2026-03-07 22:39:56.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','Lager',20,0,0,TO_TIMESTAMP('2026-03-07 22:39:56.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Produkt
-- Column: M_QtyReservation.M_Product_ID
-- 2026-03-07T22:40:06.806Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774866,0,549081,554986,648508,'F',TO_TIMESTAMP('2026-03-07 22:40:05.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',30,0,0,TO_TIMESTAMP('2026-03-07 22:40:05.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.AttributesKey (technical)
-- Column: M_QtyReservation.AttributesKey
-- 2026-03-07T22:40:16.142Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774874,0,549081,554986,648509,'F',TO_TIMESTAMP('2026-03-07 22:40:15.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','N','Y','N','N','AttributesKey (technical)',40,0,0,TO_TIMESTAMP('2026-03-07 22:40:15.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10
-- UI Element Group: qtys
-- 2026-03-07T22:40:42.212Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549277,554987,TO_TIMESTAMP('2026-03-07 22:40:42.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','qtys',20,TO_TIMESTAMP('2026-03-07 22:40:42.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.TU Anzahl
-- Column: M_QtyReservation.QtyTU
-- 2026-03-07T22:40:55.237Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774872,0,549081,554987,648510,'F',TO_TIMESTAMP('2026-03-07 22:40:55.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','TU Anzahl',10,0,0,TO_TIMESTAMP('2026-03-07 22:40:55.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.Menge
-- Column: M_QtyReservation.Qty
-- 2026-03-07T22:41:20.839Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774873,0,549081,554987,648511,'F',TO_TIMESTAMP('2026-03-07 22:41:20.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','Menge',20,0,0,TO_TIMESTAMP('2026-03-07 22:41:20.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.Gelieferte Menge
-- Column: M_QtyReservation.QtyDelivered
-- 2026-03-07T22:41:27.499Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774875,0,549081,554987,648512,'F',TO_TIMESTAMP('2026-03-07 22:41:27.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','N','N','Gelieferte Menge',30,0,0,TO_TIMESTAMP('2026-03-07 22:41:27.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.Maßeinheit
-- Column: M_QtyReservation.C_UOM_ID
-- 2026-03-07T22:41:38.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774871,0,549081,554987,648513,'F',TO_TIMESTAMP('2026-03-07 22:41:38.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',40,0,0,TO_TIMESTAMP('2026-03-07 22:41:38.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10
-- UI Element Group: misc
-- 2026-03-07T22:41:58.606Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549277,554988,TO_TIMESTAMP('2026-03-07 22:41:58.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','misc',30,TO_TIMESTAMP('2026-03-07 22:41:58.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> misc.Zugesagter Termin
-- Column: M_QtyReservation.DatePromised
-- 2026-03-07T22:42:09.845Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774870,0,549081,554988,648514,'F',TO_TIMESTAMP('2026-03-07 22:42:08.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','Zugesagter Termin',10,0,0,TO_TIMESTAMP('2026-03-07 22:42:08.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> misc.Versorgungsart
-- Column: M_QtyReservation.SupplyType
-- 2026-03-07T22:42:26.810Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774869,0,549081,554988,648515,'F',TO_TIMESTAMP('2026-03-07 22:42:26.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Versorgungsart',20,0,0,TO_TIMESTAMP('2026-03-07 22:42:26.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> misc.Lieferant
-- Column: M_QtyReservation.C_BPartner_Vendor_ID
-- 2026-03-07T22:42:33.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774868,0,549081,554988,648516,'F',TO_TIMESTAMP('2026-03-07 22:42:33.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Lieferant',30,0,0,TO_TIMESTAMP('2026-03-07 22:42:33.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 20
-- UI Element Group: flags
-- 2026-03-07T22:42:43.017Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549278,554989,TO_TIMESTAMP('2026-03-07 22:42:42.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2026-03-07 22:42:42.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 20 -> flags.Aktiv
-- Column: M_QtyReservation.IsActive
-- 2026-03-07T22:42:52.692Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774864,0,549081,554989,648517,'F',TO_TIMESTAMP('2026-03-07 22:42:51.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2026-03-07 22:42:51.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 20 -> flags.Verarbeitet
-- Column: M_QtyReservation.Processed
-- 2026-03-07T22:42:58.932Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774876,0,549081,554989,648518,'F',TO_TIMESTAMP('2026-03-07 22:42:58.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',20,0,0,TO_TIMESTAMP('2026-03-07 22:42:58.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 20
-- UI Element Group: org
-- 2026-03-07T22:43:04.104Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549278,554990,TO_TIMESTAMP('2026-03-07 22:43:03.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-03-07 22:43:03.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 20 -> org.Sektion
-- Column: M_QtyReservation.AD_Org_ID
-- 2026-03-07T22:43:14.087Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774863,0,549081,554990,648519,'F',TO_TIMESTAMP('2026-03-07 22:43:12.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2026-03-07 22:43:12.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Auftragsposition
-- Column: M_QtyReservation.C_OrderLine_ID
-- 2026-03-07T22:44:01.566Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648506
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Lager
-- Column: M_QtyReservation.M_Warehouse_ID
-- 2026-03-07T22:44:01.573Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648507
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Produkt
-- Column: M_QtyReservation.M_Product_ID
-- 2026-03-07T22:44:01.581Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648508
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.AttributesKey (technical)
-- Column: M_QtyReservation.AttributesKey
-- 2026-03-07T22:44:01.587Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648509
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.TU Anzahl
-- Column: M_QtyReservation.QtyTU
-- 2026-03-07T22:44:01.593Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648510
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.Gelieferte Menge
-- Column: M_QtyReservation.QtyDelivered
-- 2026-03-07T22:44:01.601Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648512
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> qtys.Menge
-- Column: M_QtyReservation.Qty
-- 2026-03-07T22:44:01.607Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648511
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> misc.Zugesagter Termin
-- Column: M_QtyReservation.DatePromised
-- 2026-03-07T22:44:01.614Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648514
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 20 -> flags.Verarbeitet
-- Column: M_QtyReservation.Processed
-- 2026-03-07T22:44:01.620Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648518
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> misc.Versorgungsart
-- Column: M_QtyReservation.SupplyType
-- 2026-03-07T22:44:01.628Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-07 22:44:01.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648515
;

-- Column: M_QtyReservation.AD_Org_ID
-- 2026-03-07T22:44:28.528Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:44:28.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592096
;

-- Column: M_QtyReservation.AttributesKey
-- 2026-03-07T22:44:32.412Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:44:32.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592111
;

-- Column: M_QtyReservation.C_BPartner_Vendor_ID
-- 2026-03-07T22:44:34.663Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:44:34.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592105
;

-- Column: M_QtyReservation.C_OrderLine_ID
-- 2026-03-07T22:44:37.147Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:44:37.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592102
;

-- Column: M_QtyReservation.Created
-- 2026-03-07T22:44:40.917Z
UPDATE AD_Column SET FilterOperator='E',Updated=TO_TIMESTAMP('2026-03-07 22:44:40.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592098
;

-- Column: M_QtyReservation.M_Product_ID
-- 2026-03-07T22:44:53.906Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:44:53.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592103
;

-- Column: M_QtyReservation.M_Warehouse_ID
-- 2026-03-07T22:44:57.667Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:44:57.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592104
;

-- Column: M_QtyReservation.Processed
-- 2026-03-07T22:45:01.895Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:45:01.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=579379/*OLD=592131*/
;

-- Column: M_QtyReservation.SupplyType
-- 2026-03-07T22:45:07.509Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:45:07.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592106
;

-- Window: Mengenreservierung, InternalName=qtyReservation
-- 2026-03-07T22:46:13.498Z
UPDATE AD_Window SET InternalName='qtyReservation',Updated=TO_TIMESTAMP('2026-03-07 22:46:13.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542106
;

-- Name: Qty Reservation
-- Action Type: W
-- Window: Mengenreservierung(542106,D)
-- 2026-03-07T22:46:19.499Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584594,542304,0,542106,TO_TIMESTAMP('2026-03-07 22:46:19.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','qtyReservation','Y','N','N','N','N','Qty Reservation',TO_TIMESTAMP('2026-03-07 22:46:19.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:46:19.504Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542304 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-03-07T22:46:19.507Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542304, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542304)
;

-- 2026-03-07T22:46:19.529Z
/* DDL */  select update_menu_translation_from_ad_element(584594)
;

-- Reordering children of `Sales`
-- Node name: `CreditPass configuration (CS_Creditpass_Config)`
-- 2026-03-07T22:46:20.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order (C_Order)`
-- 2026-03-07T22:46:20.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Sales Statistics (C_Order_M_InOut_C_Invoice_Overview_V)`
-- 2026-03-07T22:46:20.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542296 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request (Alberta_PrescriptionRequest)`
-- 2026-03-07T22:46:20.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (C_OLCand)`
-- 2026-03-07T22:46:20.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (EDI-Import) (Legacy-EDI-Import) (C_OLCand)`
-- 2026-03-07T22:46:20.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542255 AND AD_Tree_ID=10
;

-- Node name: `Order Control (C_Order_MFGWarehouse_Report)`
-- 2026-03-07T22:46:20.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2026-03-07T22:46:20.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type (C_CreditLimit_Type)`
-- 2026-03-07T22:46:20.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-03-07T22:46:20.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-03-07T22:46:20.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-03-07T22:46:20.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results (CS_Transaction_Result)`
-- 2026-03-07T22:46:20.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2026-03-07T22:46:20.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm (C_Incoterms)`
-- 2026-03-07T22:46:20.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Available for sales (MD_Available_For_Sales)`
-- 2026-03-07T22:46:20.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541962 AND AD_Tree_ID=10
;

-- Node name: `Point of Sale (POS)`
-- 2026-03-07T22:46:20.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542171 AND AD_Tree_ID=10
;

-- Node name: `Mengenreservierung`
-- 2026-03-07T22:46:20.119Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542304 AND AD_Tree_ID=10
;

-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:47:26.456Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592205,558,0,30,542583,'XX','C_Order_ID',TO_TIMESTAMP('2026-03-07 22:47:26.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Auftrag','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2026-03-07 22:47:26.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-07T22:47:26.460Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592205 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-07T22:47:26.465Z
/* DDL */  select update_Column_Translation_From_AD_Element(558)
;

-- 2026-03-07T22:47:27.144Z
/* DDL */ SELECT public.db_alter_table('M_QtyReservation','ALTER TABLE public.M_QtyReservation ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2026-03-07T22:47:27.368Z
ALTER TABLE M_QtyReservation ADD CONSTRAINT COrder_MQtyReservation FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:47:34.219Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-03-07 22:47:34.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592205
;

-- Table: M_QtyReservation
-- 2026-03-07T22:48:02.637Z
UPDATE AD_Table SET IsChangeLog='N', IsHighVolume='Y',Updated=TO_TIMESTAMP('2026-03-07 22:48:02.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542583
;

-- Tab: Mengenreservierung(542106,D) -> Mengenreservierung
-- Table: M_QtyReservation
-- 2026-03-07T22:48:14.464Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2026-03-07 22:48:14.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=549081
;

-- Field: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> Auftrag
-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:48:26.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592205,774877,0,549081,TO_TIMESTAMP('2026-03-07 22:48:26.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2026-03-07 22:48:26.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T22:48:26.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-07T22:48:26.373Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2026-03-07T22:48:26.389Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774877
;

-- 2026-03-07T22:48:26.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774877)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Auftrag
-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:48:53.174Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774877,0,549081,554986,648520,'F',TO_TIMESTAMP('2026-03-07 22:48:52.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',50,0,0,TO_TIMESTAMP('2026-03-07 22:48:52.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Auftrag
-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:49:13.308Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2026-03-07 22:49:13.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648520
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Auftragsposition
-- Column: M_QtyReservation.C_OrderLine_ID
-- 2026-03-07T22:49:29.060Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-03-07 22:49:29.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648506
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Lager
-- Column: M_QtyReservation.M_Warehouse_ID
-- 2026-03-07T22:49:29.067Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-03-07 22:49:29.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648507
;

-- UI Element: Mengenreservierung(542106,D) -> Mengenreservierung(549081,D) -> main -> 10 -> primary.Auftrag
-- Column: M_QtyReservation.C_Order_ID
-- 2026-03-07T22:49:29.072Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-07 22:49:29.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648520
;

-- Column: M_QtyReservation.C_OrderLine_ID
-- 2026-03-07T22:49:43.611Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2026-03-07 22:49:43.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592102
;

-- Column: M_QtyReservation.C_BPartner_Vendor_ID
-- 2026-03-07T22:50:43.228Z
UPDATE AD_Column SET AD_Reference_Value_ID=541252,Updated=TO_TIMESTAMP('2026-03-07 22:50:43.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592105
;

