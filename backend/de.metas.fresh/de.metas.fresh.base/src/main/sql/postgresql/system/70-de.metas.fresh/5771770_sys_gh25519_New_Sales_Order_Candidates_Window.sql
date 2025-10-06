-- Run mode: SWING_CLIENT

-- 2025-09-30T14:52:54.581Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584049,0,TO_TIMESTAMP('2025-09-30 14:52:53.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Auftragsdisposition (Legacy-EDI-Import)','Auftragsdisposition (Legacy-EDI-Import)',TO_TIMESTAMP('2025-09-30 14:52:53.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:52:54.645Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584049 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Auftragsdisposition (Legacy-EDI-Import), InternalName=C_OLCand
-- 2025-09-30T14:53:20.936Z
UPDATE AD_Window SET AD_Element_ID=584049, Description=NULL, Help=NULL, Name='Auftragsdisposition (Legacy-EDI-Import)',Updated=TO_TIMESTAMP('2025-09-30 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540095
;

-- 2025-09-30T14:53:20.994Z
UPDATE AD_Window_Trl trl SET Name='Auftragsdisposition (Legacy-EDI-Import)' WHERE AD_Window_ID=540095 AND AD_Language='de_DE'
;

-- Name: Auftragsdisposition (Legacy-EDI-Import)
-- Action Type: W
-- Window: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate)
-- 2025-09-30T14:53:21.464Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Auftragsdisposition (Legacy-EDI-Import)',Updated=TO_TIMESTAMP('2025-09-30 14:53:21.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=1000097
;

-- 2025-09-30T14:53:21.521Z
UPDATE AD_Menu_Trl trl SET Name='Auftragsdisposition (Legacy-EDI-Import)' WHERE AD_Menu_ID=1000097 AND AD_Language='de_DE'
;

-- Name: Auftragsdisposition (Legacy-EDI-Import)
-- Action Type: W
-- Window: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate)
-- 2025-09-30T14:53:21.852Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Auftragsdisposition (Legacy-EDI-Import)',Updated=TO_TIMESTAMP('2025-09-30 14:53:21.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540241
;

-- 2025-09-30T14:53:21.909Z
UPDATE AD_Menu_Trl trl SET Name='Auftragsdisposition (Legacy-EDI-Import)' WHERE AD_Menu_ID=540241 AND AD_Language='de_DE'
;

-- 2025-09-30T14:53:22.166Z
/* DDL */  select update_window_translation_from_ad_element(584049)
;

-- 2025-09-30T14:53:22.238Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540095
;

-- 2025-09-30T14:53:22.306Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(540095)
;

------------------------------------------------------------

------------------------------
-- Update custom windows that might exist in the respective DB
-- Note that Overrides_Window_ID is not reliable because custom C_OLCand windows might be older than that column.
------------------------------
create table fix.c_olcand_window_update_name as
select ad_window_id, ad_element_id, name, overrides_window_id, replace(name, 'Auftragsdisposition', 'Auftragsdisposition (Legacy-EDI-Import)') as new_name 
from ad_window
where ad_window_id in (select ad_window_id from ad_tab where ad_table_id = get_table_id('c_olcand'))
and name ilike '%Auftragsdisposition%';

UPDATE AD_Element e set Name=d.new_name, Updated=TO_TIMESTAMP('2025-10-06 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
from fix.c_olcand_window_update_name d where d.ad_element_id=e.ad_element_id;

UPDATE AD_Element_Trl e set Name=d.new_name, Updated=TO_TIMESTAMP('2025-10-06 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
from fix.c_olcand_window_update_name d where d.ad_element_id=e.ad_element_id;

UPDATE AD_Window w set Name=d.new_name, Updated=TO_TIMESTAMP('2025-10-06 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
from fix.c_olcand_window_update_name d where d.ad_window_id=w.ad_window_id;

UPDATE AD_Window_Trl w set Name=d.new_name, Updated=TO_TIMESTAMP('2025-10-06 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
from fix.c_olcand_window_update_name d where d.ad_window_id=w.ad_window_id;

UPDATE AD_Menu m set Name=d.new_name, Updated=TO_TIMESTAMP('2025-10-06 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
from fix.c_olcand_window_update_name d where d.ad_window_id=m.ad_window_id;

UPDATE AD_Menu_Trl mt set Name=d.new_name, Updated=TO_TIMESTAMP('2025-10-06 14:53:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
FROM fix.c_olcand_window_update_name d
WHERE mt.ad_menu_id IN (SELECT ad_menu_id FROM ad_menu m WHERE m.ad_window_id = d.ad_window_id)
;

DELETE FROM AD_Element_Link WHERE AD_Window_ID IN (SELECT ad_window_id FROM fix.c_olcand_window_update_name)
;

select AD_Element_Link_Create_Missing_Window(ad_window_id) from fix.c_olcand_window_update_name;
---------------------------------------------


    

-- Window: Auftragsdisposition, InternalName=null
-- 2025-09-30T14:54:22.652Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,574745,0,541952,TO_TIMESTAMP('2025-09-30 14:54:21.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Auftragsdisposition','N',TO_TIMESTAMP('2025-09-30 14:54:21.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-09-30T14:54:22.712Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541952 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-09-30T14:54:22.831Z
/* DDL */  select update_window_translation_from_ad_element(574745)
;

-- 2025-09-30T14:54:22.896Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541952
;

-- 2025-09-30T14:54:22.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541952)
;

-- Window: Auftragsdisposition, InternalName=null
-- 2025-09-30T14:54:49.517Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='de.metas.ordercandidate', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=540095, WindowType='T', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2025-09-30 14:54:49.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541952
;

-- 2025-09-30T14:54:49.748Z
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541952
;

-- 2025-09-30T14:54:49.806Z
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541952, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540095
;

-- Tab: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat
-- Table: C_OLCand
-- 2025-09-30T14:54:50.862Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573636,0,548442,540244,541952,'Y',TO_TIMESTAMP('2025-09-30 14:54:49.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ordercandidate','N','N','A','C_OLCand','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','N','N','N','N','Kandidat','N',10,0,TO_TIMESTAMP('2025-09-30 14:54:49.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:50.919Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548442 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-09-30T14:54:50.976Z
/* DDL */  select update_tab_translation_from_ad_element(573636)
;

-- 2025-09-30T14:54:51.042Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548442)
;

-- 2025-09-30T14:54:51.158Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548442
;

-- 2025-09-30T14:54:51.216Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548442, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540282
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Aktiv
-- Column: C_OLCand.IsActive
-- 2025-09-30T14:54:52.707Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544279,754227,0,548442,0,TO_TIMESTAMP('2025-09-30 14:54:51.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.ordercandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',10,10,1,1,TO_TIMESTAMP('2025-09-30 14:54:51.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:52.766Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754227 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:54:52.825Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-09-30T14:54:52.983Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754227
;

-- 2025-09-30T14:54:53.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754227)
;

-- 2025-09-30T14:54:53.158Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754227
;

-- 2025-09-30T14:54:53.215Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754227, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547043
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Aktualisiert
-- Column: C_OLCand.Updated
-- 2025-09-30T14:54:54.157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544281,754228,0,548442,0,TO_TIMESTAMP('2025-09-30 14:54:53.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'de.metas.ordercandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Aktualisiert',20,0,1,1,TO_TIMESTAMP('2025-09-30 14:54:53.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:54.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754228 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:54:54.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-09-30T14:54:54.362Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754228
;

-- 2025-09-30T14:54:54.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754228)
;

-- 2025-09-30T14:54:54.534Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754228
;

-- 2025-09-30T14:54:54.592Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754228, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547044
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Aktualisiert durch
-- Column: C_OLCand.UpdatedBy
-- 2025-09-30T14:54:55.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544282,754229,0,548442,0,TO_TIMESTAMP('2025-09-30 14:54:54.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'de.metas.ordercandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Aktualisiert durch',30,0,1,1,TO_TIMESTAMP('2025-09-30 14:54:54.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:55.593Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754229 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:54:55.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-09-30T14:54:55.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754229
;

-- 2025-09-30T14:54:55.832Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754229)
;

-- 2025-09-30T14:54:55.947Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754229
;

-- 2025-09-30T14:54:56.003Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754229, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547045
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Auftragskandidat
-- Column: C_OLCand.C_OLCand_ID
-- 2025-09-30T14:54:57.187Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544275,754230,1002449,0,548442,0,TO_TIMESTAMP('2025-09-30 14:54:56.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Auftragskandidat',40,0,1,1,TO_TIMESTAMP('2025-09-30 14:54:56.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:57.246Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:54:57.303Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002449)
;

-- 2025-09-30T14:54:57.361Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754230
;

-- 2025-09-30T14:54:57.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754230)
;

-- 2025-09-30T14:54:57.536Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754230
;

-- 2025-09-30T14:54:57.595Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754230, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547046
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Merkmale
-- Column: C_OLCand.M_AttributeSetInstance_ID
-- 2025-09-30T14:54:58.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544287,754231,0,548442,0,TO_TIMESTAMP('2025-09-30 14:54:57.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',100,'de.metas.ordercandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Merkmale',430,0,999,1,TO_TIMESTAMP('2025-09-30 14:54:57.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:58.548Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:54:58.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2025-09-30T14:54:58.671Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754231
;

-- 2025-09-30T14:54:58.728Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754231)
;

-- 2025-09-30T14:54:58.845Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754231
;

-- 2025-09-30T14:54:58.903Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754231, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547047
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Beschreibung
-- Column: C_OLCand.Description
-- 2025-09-30T14:54:59.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544278,754232,0,548442,244,TO_TIMESTAMP('2025-09-30 14:54:58.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Beschreibung',210,0,999,1,TO_TIMESTAMP('2025-09-30 14:54:58.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:54:59.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:54:59.892Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-09-30T14:54:59.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754232
;

-- 2025-09-30T14:55:00.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754232)
;

-- 2025-09-30T14:55:00.169Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754232
;

-- 2025-09-30T14:55:00.228Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754232, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547048
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Erfasst durch
-- Column: C_OLCand.AD_User_EnteredBy_ID
-- 2025-09-30T14:55:01.125Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544980,754233,0,548442,116,TO_TIMESTAMP('2025-09-30 14:55:00.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Erfasst durch',30,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:00.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:01.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:01.244Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541288)
;

-- 2025-09-30T14:55:01.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754233
;

-- 2025-09-30T14:55:01.361Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754233)
;

-- 2025-09-30T14:55:01.479Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754233
;

-- 2025-09-30T14:55:01.540Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754233, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547049
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Erstellt
-- Column: C_OLCand.Created
-- 2025-09-30T14:55:02.479Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544276,754234,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:01.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',7,'de.metas.ordercandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Erstellt',440,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:01.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:02.541Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:02.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-09-30T14:55:02.697Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754234
;

-- 2025-09-30T14:55:02.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754234)
;

-- 2025-09-30T14:55:02.873Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754234
;

-- 2025-09-30T14:55:02.930Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754234, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547050
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Erstellt durch
-- Column: C_OLCand.CreatedBy
-- 2025-09-30T14:55:03.894Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544277,754235,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:02.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',22,'de.metas.ordercandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Erstellt durch',450,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:02.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:03.954Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:04.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-09-30T14:55:04.128Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754235
;

-- 2025-09-30T14:55:04.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754235)
;

-- 2025-09-30T14:55:04.301Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754235
;

-- 2025-09-30T14:55:04.362Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754235, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547051
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Geschäftspartner
-- Column: C_OLCand.C_BPartner_ID
-- 2025-09-30T14:55:05.256Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544977,754236,0,548442,305,TO_TIMESTAMP('2025-09-30 14:55:04.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'de.metas.ordercandidate','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Geschäftspartner',230,10,1,1,TO_TIMESTAMP('2025-09-30 14:55:04.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:05.316Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:05.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-09-30T14:55:05.444Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754236
;

-- 2025-09-30T14:55:05.503Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754236)
;

-- 2025-09-30T14:55:05.619Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754236
;

-- 2025-09-30T14:55:05.678Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754236, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547052
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Kand.-Datum
-- Column: C_OLCand.DateCandidate
-- 2025-09-30T14:55:06.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544979,754237,0,548442,135,TO_TIMESTAMP('2025-09-30 14:55:05.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Kand.-Datum',20,0,-1,1,1,TO_TIMESTAMP('2025-09-30 14:55:05.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:06.617Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:06.676Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541287)
;

-- 2025-09-30T14:55:06.737Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754237
;

-- 2025-09-30T14:55:06.799Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754237)
;

-- 2025-09-30T14:55:06.915Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754237
;

-- 2025-09-30T14:55:06.972Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754237, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547053
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Kosten
-- Column: C_OLCand.C_Charge_ID
-- 2025-09-30T14:55:07.907Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544284,754238,0,548442,110,TO_TIMESTAMP('2025-09-30 14:55:07.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zusätzliche Kosten',10,'de.metas.ordercandidate','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)',0,'Y','N','N','N','N','N','N','N','N','Y','N','Kosten',460,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:07.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:07.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:08.022Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(968)
;

-- 2025-09-30T14:55:08.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754238
;

-- 2025-09-30T14:55:08.139Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754238)
;

-- 2025-09-30T14:55:08.255Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754238
;

-- 2025-09-30T14:55:08.313Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754238, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547054
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Mandant
-- Column: C_OLCand.AD_Client_ID
-- 2025-09-30T14:55:09.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544272,754239,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:08.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'de.metas.ordercandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',470,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:08.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:09.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754239 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:09.374Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-09-30T14:55:09.504Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754239
;

-- 2025-09-30T14:55:09.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754239)
;

-- 2025-09-30T14:55:09.681Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754239
;

-- 2025-09-30T14:55:09.738Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754239, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547055
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Maßeinheit
-- Column: C_OLCand.C_UOM_ID
-- 2025-09-30T14:55:10.610Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544976,754240,0,548442,10,TO_TIMESTAMP('2025-09-30 14:55:09.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'de.metas.ordercandidate','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Maßeinheit',150,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:09.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:10.669Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:10.727Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-09-30T14:55:10.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754240
;

-- 2025-09-30T14:55:10.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754240)
;

-- 2025-09-30T14:55:10.972Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754240
;

-- 2025-09-30T14:55:11.028Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754240, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547056
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Menge
-- Column: C_OLCand.QtyEntered
-- 2025-09-30T14:55:11.923Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544285,754241,0,548442,52,TO_TIMESTAMP('2025-09-30 14:55:11.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit',14,'de.metas.ordercandidate','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Menge',130,50,1,1,TO_TIMESTAMP('2025-09-30 14:55:11.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:11.980Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:12.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2589)
;

-- 2025-09-30T14:55:12.099Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754241
;

-- 2025-09-30T14:55:12.156Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754241)
;

-- 2025-09-30T14:55:12.273Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754241
;

-- 2025-09-30T14:55:12.330Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754241, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547057
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Merkmals-Satz
-- Column: C_OLCand.M_AttributeSet_ID
-- 2025-09-30T14:55:13.252Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544310,754242,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:12.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals-Satz zum Produkt',10,'de.metas.ordercandidate','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Merkmals-Satz',480,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:12.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:13.310Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:13.369Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2017)
;

-- 2025-09-30T14:55:13.434Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754242
;

-- 2025-09-30T14:55:13.489Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754242)
;

-- 2025-09-30T14:55:13.603Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754242
;

-- 2025-09-30T14:55:13.660Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754242, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547058
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Sektion
-- Column: C_OLCand.AD_Org_ID
-- 2025-09-30T14:55:14.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544273,754243,0,548442,71,TO_TIMESTAMP('2025-09-30 14:55:13.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'de.metas.ordercandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Sektion',490,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:13.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:14.659Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:14.721Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-09-30T14:55:14.873Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754243
;

-- 2025-09-30T14:55:14.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754243)
;

-- 2025-09-30T14:55:15.048Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754243
;

-- 2025-09-30T14:55:15.103Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754243, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547060
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Produkt
-- Column: C_OLCand.M_Product_ID
-- 2025-09-30T14:55:15.969Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544283,754244,0,548442,59,TO_TIMESTAMP('2025-09-30 14:55:15.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'de.metas.ordercandidate','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Produkt',70,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:15.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:16.033Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:16.094Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-09-30T14:55:16.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754244
;

-- 2025-09-30T14:55:16.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754244)
;

-- 2025-09-30T14:55:16.361Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754244
;

-- 2025-09-30T14:55:16.418Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754244, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547061
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Standort
-- Column: C_OLCand.C_BPartner_Location_ID
-- 2025-09-30T14:55:17.241Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544286,754245,1002243,0,548442,92,TO_TIMESTAMP('2025-09-30 14:55:16.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.ordercandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Standort',240,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:16.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:17.300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:17.358Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002243)
;

-- 2025-09-30T14:55:17.416Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754245
;

-- 2025-09-30T14:55:17.473Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754245)
;

-- 2025-09-30T14:55:17.591Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754245
;

-- 2025-09-30T14:55:17.647Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754245, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547062
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferkontakt
-- Column: C_OLCand.AD_User_ID
-- 2025-09-30T14:55:18.469Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544978,754246,1002785,0,548442,86,TO_TIMESTAMP('2025-09-30 14:55:17.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',10,'de.metas.ordercandidate','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Lieferkontakt',250,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:17.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:18.527Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:18.585Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002785)
;

-- 2025-09-30T14:55:18.645Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754246
;

-- 2025-09-30T14:55:18.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754246)
;

-- 2025-09-30T14:55:18.814Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754246
;

-- 2025-09-30T14:55:18.871Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754246, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547063
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Verarb.
-- Column: C_OLCand.Processed
-- 2025-09-30T14:55:19.706Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544984,754247,578639,0,548442,48,TO_TIMESTAMP('2025-09-30 14:55:18.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'de.metas.ordercandidate','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Verarb.',500,80,1,1,TO_TIMESTAMP('2025-09-30 14:55:18.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:19.766Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:19.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578639)
;

-- 2025-09-30T14:55:19.891Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754247
;

-- 2025-09-30T14:55:19.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754247)
;

-- 2025-09-30T14:55:20.069Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754247
;

-- 2025-09-30T14:55:20.129Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754247, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547094
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Eingabequelle
-- Column: C_OLCand.AD_InputDataSource_ID
-- 2025-09-30T14:55:20.936Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,544998,754248,1002889,0,548442,148,TO_TIMESTAMP('2025-09-30 14:55:20.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Eingabequelle',50,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:20.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:20.993Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:21.054Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002889)
;

-- 2025-09-30T14:55:21.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754248
;

-- 2025-09-30T14:55:21.175Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754248)
;

-- 2025-09-30T14:55:21.291Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754248
;

-- 2025-09-30T14:55:21.351Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754248, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 547478
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Fehler
-- Column: C_OLCand.IsError
-- 2025-09-30T14:55:22.240Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546225,754249,0,548442,49,TO_TIMESTAMP('2025-09-30 14:55:21.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Fehler ist bei der Durchführung aufgetreten',1,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Fehler',510,150,1,1,TO_TIMESTAMP('2025-09-30 14:55:21.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:22.298Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:22.358Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395)
;

-- 2025-09-30T14:55:22.424Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754249
;

-- 2025-09-30T14:55:22.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754249)
;

-- 2025-09-30T14:55:22.603Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754249
;

-- 2025-09-30T14:55:22.661Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754249, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548747
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Statusmeldung
-- Column: C_OLCand.AD_Note_ID
-- 2025-09-30T14:55:23.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546232,754250,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:22.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'System-Nachricht',512,'@IsError@=''Y''','de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Statusmeldung',520,0,0,999,1,TO_TIMESTAMP('2025-09-30 14:55:22.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:23.658Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:23.716Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1348)
;

-- 2025-09-30T14:55:23.776Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754250
;

-- 2025-09-30T14:55:23.833Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754250)
;

-- 2025-09-30T14:55:23.951Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754250
;

-- 2025-09-30T14:55:24.010Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754250, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 548754
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Ziel-Lager
-- Column: C_OLCand.M_Warehouse_Dest_ID
-- 2025-09-30T14:55:24.887Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546475,754251,0,548442,70,TO_TIMESTAMP('2025-09-30 14:55:24.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Ziel-Lager',410,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:24.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:24.945Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:25.004Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541643)
;

-- 2025-09-30T14:55:25.070Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754251
;

-- 2025-09-30T14:55:25.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754251)
;

-- 2025-09-30T14:55:25.243Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754251
;

-- 2025-09-30T14:55:25.300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754251, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549447
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Fehlermeldung
-- Column: C_OLCand.ErrorMsg
-- 2025-09-30T14:55:26.182Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,546488,754252,0,548442,189,TO_TIMESTAMP('2025-09-30 14:55:25.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'@IsError@=''Y''','de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Fehlermeldung',520,160,0,999,1,TO_TIMESTAMP('2025-09-30 14:55:25.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:26.243Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:26.301Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1021)
;

-- 2025-09-30T14:55:26.361Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754252
;

-- 2025-09-30T14:55:26.418Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754252)
;

-- 2025-09-30T14:55:26.533Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754252
;

-- 2025-09-30T14:55:26.591Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754252, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 549595
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Manueller Preis
-- Column: C_OLCand.IsManualPrice
-- 2025-09-30T14:55:27.476Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547046,754253,1000867,0,548442,98,TO_TIMESTAMP('2025-09-30 14:55:26.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Manueller Preis',530,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:26.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:27.535Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:27.593Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000867)
;

-- 2025-09-30T14:55:27.655Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754253
;

-- 2025-09-30T14:55:27.717Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754253)
;

-- 2025-09-30T14:55:27.833Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754253
;

-- 2025-09-30T14:55:27.889Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754253, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550284
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Manueller Rabatt
-- Column: C_OLCand.IsManualDiscount
-- 2025-09-30T14:55:28.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547047,754254,0,548442,153,TO_TIMESTAMP('2025-09-30 14:55:27.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Rabatt, der von Hand eingetragen wurde, wird vom Provisionssystem nicht überschrieben',1,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Manueller Rabatt',540,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:27.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:28.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:28.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540649)
;

-- 2025-09-30T14:55:29.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754254
;

-- 2025-09-30T14:55:29.073Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754254)
;

-- 2025-09-30T14:55:29.188Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754254
;

-- 2025-09-30T14:55:29.245Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754254, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550285
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Preis
-- Column: C_OLCand.PriceEntered
-- 2025-09-30T14:55:30.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547039,754255,0,548442,59,TO_TIMESTAMP('2025-09-30 14:55:29.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit',14,'de.metas.ordercandidate','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Preis',440,110,1,1,TO_TIMESTAMP('2025-09-30 14:55:29.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:30.178Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:30.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2588)
;

-- 2025-09-30T14:55:30.294Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754255
;

-- 2025-09-30T14:55:30.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754255)
;

-- 2025-09-30T14:55:30.469Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754255
;

-- 2025-09-30T14:55:30.526Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754255, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550286
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rabatt %
-- Column: C_OLCand.Discount
-- 2025-09-30T14:55:31.701Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547040,754256,0,548442,64,TO_TIMESTAMP('2025-09-30 14:55:30.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Abschlag in Prozent',14,'@IsManualDiscount/''N''@=''Y''','de.metas.ordercandidate','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Rabatt %',490,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:30.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:31.758Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:31.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(280)
;

-- 2025-09-30T14:55:31.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754256
;

-- 2025-09-30T14:55:31.941Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754256)
;

-- 2025-09-30T14:55:32.057Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754256
;

-- 2025-09-30T14:55:32.114Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754256, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550287
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rechnungskontakt
-- Column: C_OLCand.Bill_User_ID
-- 2025-09-30T14:55:32.981Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547043,754257,0,548442,116,TO_TIMESTAMP('2025-09-30 14:55:32.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Rechnungskontakt',340,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:32.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:33.040Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:33.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2041)
;

-- 2025-09-30T14:55:33.163Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754257
;

-- 2025-09-30T14:55:33.220Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754257)
;

-- 2025-09-30T14:55:33.337Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754257
;

-- 2025-09-30T14:55:33.395Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754257, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550288
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rechnungspartner
-- Column: C_OLCand.Bill_BPartner_ID
-- 2025-09-30T14:55:34.273Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547041,754258,0,548442,114,TO_TIMESTAMP('2025-09-30 14:55:33.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartner für die Rechnungsstellung',10,'de.metas.ordercandidate','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Rechnungspartner',320,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:33.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:34.331Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:34.391Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039)
;

-- 2025-09-30T14:55:34.459Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754258
;

-- 2025-09-30T14:55:34.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754258)
;

-- 2025-09-30T14:55:34.635Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754258
;

-- 2025-09-30T14:55:34.693Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754258, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550289
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rechnungsstandort
-- Column: C_OLCand.Bill_Location_ID
-- 2025-09-30T14:55:35.563Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547042,754259,0,548442,120,TO_TIMESTAMP('2025-09-30 14:55:34.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Rechnungsstandort',330,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:34.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:35.620Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:35.679Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2040)
;

-- 2025-09-30T14:55:35.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754259
;

-- 2025-09-30T14:55:35.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754259)
;

-- 2025-09-30T14:55:35.922Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754259
;

-- 2025-09-30T14:55:35.980Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754259, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550290
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Währung
-- Column: C_OLCand.C_Currency_ID
-- 2025-09-30T14:55:36.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547045,754260,0,548442,66,TO_TIMESTAMP('2025-09-30 14:55:36.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'de.metas.ordercandidate','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Währung',480,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:36.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:36.926Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:36.986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2025-09-30T14:55:37.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754260
;

-- 2025-09-30T14:55:37.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754260)
;

-- 2025-09-30T14:55:37.244Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754260
;

-- 2025-09-30T14:55:37.301Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754260, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550292
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Vertragsbedingungen
-- Column: C_OLCand.C_Flatrate_Conditions_ID
-- 2025-09-30T14:55:38.162Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547321,754261,1002900,0,548442,131,TO_TIMESTAMP('2025-09-30 14:55:37.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Vertragsbedingungen',550,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:37.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:38.219Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:38.278Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002900)
;

-- 2025-09-30T14:55:38.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754261
;

-- 2025-09-30T14:55:38.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754261)
;

-- 2025-09-30T14:55:38.515Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754261
;

-- 2025-09-30T14:55:38.573Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754261, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 550553
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> DB-Tabelle
-- Column: C_OLCand.AD_Table_ID
-- 2025-09-30T14:55:39.515Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547713,754262,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:38.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',0,'de.metas.ordercandidate','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','N','N','N','Y','N','DB-Tabelle',560,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:38.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:39.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:39.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2025-09-30T14:55:39.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754262
;

-- 2025-09-30T14:55:39.769Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754262)
;

-- 2025-09-30T14:55:39.884Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754262
;

-- 2025-09-30T14:55:39.942Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754262, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551028
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Datensatz-ID
-- Column: C_OLCand.Record_ID
-- 2025-09-30T14:55:40.825Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547714,754263,0,548442,196,TO_TIMESTAMP('2025-09-30 14:55:40.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',0,'de.metas.ordercandidate','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Datensatz-ID',40,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:40.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:40.886Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:40.943Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2025-09-30T14:55:41.008Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754263
;

-- 2025-09-30T14:55:41.064Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754263)
;

-- 2025-09-30T14:55:41.181Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754263
;

-- 2025-09-30T14:55:41.238Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754263, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551029
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferart
-- Column: C_OLCand.DeliveryRule
-- 2025-09-30T14:55:42.101Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547785,754264,0,548442,60,TO_TIMESTAMP('2025-09-30 14:55:41.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen',0,'de.metas.ordercandidate','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Lieferart',300,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:41.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:42.160Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:42.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(555)
;

-- 2025-09-30T14:55:42.285Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754264
;

-- 2025-09-30T14:55:42.342Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754264)
;

-- 2025-09-30T14:55:42.457Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754264
;

-- 2025-09-30T14:55:42.515Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754264, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551069
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferung
-- Column: C_OLCand.DeliveryViaRule
-- 2025-09-30T14:55:43.405Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547786,754265,0,548442,67,TO_TIMESTAMP('2025-09-30 14:55:42.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird',0,'de.metas.ordercandidate','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Lieferung',310,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:42.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:43.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:43.521Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(274)
;

-- 2025-09-30T14:55:43.588Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754265
;

-- 2025-09-30T14:55:43.646Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754265)
;

-- 2025-09-30T14:55:43.762Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754265
;

-- 2025-09-30T14:55:43.820Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754265, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551070
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Verarbeitungsziel
-- Column: C_OLCand.AD_DataDestination_ID
-- 2025-09-30T14:55:44.689Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547734,754266,0,548442,152,TO_TIMESTAMP('2025-09-30 14:55:43.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Legt fest, welcher Teil von metafresh den jeweiligen Datensatz weiterverarbeiten soll',0,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Verarbeitungsziel',60,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:43.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:44.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:44.807Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541878)
;

-- 2025-09-30T14:55:44.870Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754266
;

-- 2025-09-30T14:55:44.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754266)
;

-- 2025-09-30T14:55:45.046Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754266
;

-- 2025-09-30T14:55:45.104Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754266, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551084
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Preissystem
-- Column: C_OLCand.M_PricingSystem_ID
-- 2025-09-30T14:55:45.971Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547766,754267,0,548442,79,TO_TIMESTAMP('2025-09-30 14:55:45.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',0,'de.metas.ordercandidate','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Preissystem',420,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:45.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:46.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:46.086Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135)
;

-- 2025-09-30T14:55:46.150Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754267
;

-- 2025-09-30T14:55:46.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754267)
;

-- 2025-09-30T14:55:46.318Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754267
;

-- 2025-09-30T14:55:46.375Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754267, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551085
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Beschreibungskopf
-- Column: C_OLCand.DescriptionHeader
-- 2025-09-30T14:55:47.249Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549010,754268,1000830,0,548442,118,TO_TIMESTAMP('2025-09-30 14:55:46.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Beschreibungskopf',570,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:46.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:47.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:47.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000830)
;

-- 2025-09-30T14:55:47.428Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754268
;

-- 2025-09-30T14:55:47.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754268)
;

-- 2025-09-30T14:55:47.602Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754268
;

-- 2025-09-30T14:55:47.658Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754268, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551901
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Schlusstext
-- Column: C_OLCand.DescriptionBottom
-- 2025-09-30T14:55:48.528Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549011,754269,1001203,0,548442,75,TO_TIMESTAMP('2025-09-30 14:55:47.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Schlusstext',580,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:47.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:48.584Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:48.642Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001203)
;

-- 2025-09-30T14:55:48.702Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754269
;

-- 2025-09-30T14:55:48.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754269)
;

-- 2025-09-30T14:55:48.872Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754269
;

-- 2025-09-30T14:55:48.928Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754269, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551902
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Referenz
-- Column: C_OLCand.POReference
-- 2025-09-30T14:55:49.813Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549849,754270,0,548442,101,TO_TIMESTAMP('2025-09-30 14:55:48.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden',0,'de.metas.ordercandidate','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Referenz',530,170,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:48.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:49.871Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:49.928Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2025-09-30T14:55:49.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754270
;

-- 2025-09-30T14:55:50.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754270)
;

-- 2025-09-30T14:55:50.169Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754270
;

-- 2025-09-30T14:55:50.228Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754270, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553469
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Zeile Nr.
-- Column: C_OLCand.Line
-- 2025-09-30T14:55:51.043Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549850,754271,1001732,0,548442,39,TO_TIMESTAMP('2025-09-30 14:55:50.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument',0,'de.metas.ordercandidate','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Zeile Nr.',540,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:50.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:51.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:51.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001732)
;

-- 2025-09-30T14:55:51.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754271
;

-- 2025-09-30T14:55:51.285Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754271)
;

-- 2025-09-30T14:55:51.401Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754271
;

-- 2025-09-30T14:55:51.458Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754271, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553470
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabeadresse
-- Column: C_OLCand.HandOver_Location_ID
-- 2025-09-30T14:55:52.581Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549940,754272,1002057,0,548442,107,TO_TIMESTAMP('2025-09-30 14:55:51.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Übergabeadresse',380,224,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:51.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:52.639Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:52.696Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002057)
;

-- 2025-09-30T14:55:52.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754272
;

-- 2025-09-30T14:55:52.811Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754272)
;

-- 2025-09-30T14:55:52.927Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754272
;

-- 2025-09-30T14:55:52.986Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754272, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553714
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabe an
-- Column: C_OLCand.HandOver_Partner_ID
-- 2025-09-30T14:55:53.803Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549941,754273,1002209,0,548442,84,TO_TIMESTAMP('2025-09-30 14:55:53.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Übergabe an',350,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:53.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:53.860Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:53.919Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002209)
;

-- 2025-09-30T14:55:53.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754273
;

-- 2025-09-30T14:55:54.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754273)
;

-- 2025-09-30T14:55:54.153Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754273
;

-- 2025-09-30T14:55:54.211Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754273, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553715
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferadresse
-- Column: C_OLCand.DropShip_Location_ID
-- 2025-09-30T14:55:55.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549981,754274,0,548442,121,TO_TIMESTAMP('2025-09-30 14:55:54.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner Location for shipping to',10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Lieferadresse',600,400,1,1,TO_TIMESTAMP('2025-09-30 14:55:54.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:55.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:55.179Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53459)
;

-- 2025-09-30T14:55:55.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754274
;

-- 2025-09-30T14:55:55.295Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754274)
;

-- 2025-09-30T14:55:55.411Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754274
;

-- 2025-09-30T14:55:55.470Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754274, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553745
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferempfänger
-- Column: C_OLCand.DropShip_BPartner_ID
-- 2025-09-30T14:55:56.282Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549982,754275,1000575,0,548442,0,TO_TIMESTAMP('2025-09-30 14:55:55.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner to ship to',10,'de.metas.ordercandidate','If empty the business partner will be shipped to.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Lieferempfänger',570,370,1,1,TO_TIMESTAMP('2025-09-30 14:55:55.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:56.341Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:56.398Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000575)
;

-- 2025-09-30T14:55:56.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754275
;

-- 2025-09-30T14:55:56.521Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754275)
;

-- 2025-09-30T14:55:56.636Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754275
;

-- 2025-09-30T14:55:56.693Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754275, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553746
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Packvorschrift
-- Column: C_OLCand.M_HU_PI_Item_Product_ID
-- 2025-09-30T14:55:57.530Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549978,754276,1001432,0,548442,202,TO_TIMESTAMP('2025-09-30 14:55:56.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Packvorschrift',100,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:56.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:57.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:57.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001432)
;

-- 2025-09-30T14:55:57.716Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754276
;

-- 2025-09-30T14:55:57.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754276)
;

-- 2025-09-30T14:55:57.890Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754276
;

-- 2025-09-30T14:55:57.947Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754276, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553756
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Verpackungskapazität
-- Column: C_OLCand.QtyItemCapacity
-- 2025-09-30T14:55:58.779Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549983,754277,1000408,0,548442,132,TO_TIMESTAMP('2025-09-30 14:55:58.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Verpackungskapazität',140,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:58.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:55:58.838Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:55:58.900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000408)
;

-- 2025-09-30T14:55:58.960Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754277
;

-- 2025-09-30T14:55:59.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754277)
;

-- 2025-09-30T14:55:59.133Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754277
;

-- 2025-09-30T14:55:59.196Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754277, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553757
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Zugesagter Termin
-- Column: C_OLCand.DatePromised
-- 2025-09-30T14:56:00.072Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550369,754278,0,548442,117,TO_TIMESTAMP('2025-09-30 14:55:59.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',0,'de.metas.ordercandidate','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugesagter Termin',180,30,0,1,1,TO_TIMESTAMP('2025-09-30 14:55:59.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:00.130Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:00.189Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2025-09-30T14:56:00.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754278
;

-- 2025-09-30T14:56:00.315Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754278)
;

-- 2025-09-30T14:56:00.432Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754278
;

-- 2025-09-30T14:56:00.489Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754278, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553994
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Replikationstransaktion
-- Column: C_OLCand.EXP_ReplicationTrx_ID
-- 2025-09-30T14:56:01.378Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550427,754279,0,548442,140,TO_TIMESTAMP('2025-09-30 14:56:00.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@EXP_ReplicationTrx_ID/-1@>0','de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Replikationstransaktion',550,180,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:00.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:01.437Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:01.497Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542389)
;

-- 2025-09-30T14:56:01.560Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754279
;

-- 2025-09-30T14:56:01.618Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754279)
;

-- 2025-09-30T14:56:01.733Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754279
;

-- 2025-09-30T14:56:01.790Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754279, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554018
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Geschäftspartner abw.
-- Column: C_OLCand.C_BPartner_Override_ID
-- 2025-09-30T14:56:02.684Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550521,754280,0,548442,52,TO_TIMESTAMP('2025-09-30 14:56:01.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',0,'de.metas.ordercandidate','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','N','N','N','N','N','N','N','N','Geschäftspartner abw.',260,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:01.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:02.743Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754280 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:02.802Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541407)
;

-- 2025-09-30T14:56:02.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754280
;

-- 2025-09-30T14:56:02.924Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754280)
;

-- 2025-09-30T14:56:03.040Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754280
;

-- 2025-09-30T14:56:03.098Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754280, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554064
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Standort abw.
-- Column: C_OLCand.C_BP_Location_Override_ID
-- 2025-09-30T14:56:03.987Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550522,754281,0,548442,90,TO_TIMESTAMP('2025-09-30 14:56:03.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',0,'de.metas.ordercandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Standort abw.',270,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:03.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:04.045Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:04.104Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541408)
;

-- 2025-09-30T14:56:04.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754281
;

-- 2025-09-30T14:56:04.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754281)
;

-- 2025-09-30T14:56:04.340Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754281
;

-- 2025-09-30T14:56:04.396Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754281, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554065
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Zugesagter Termin abw.
-- Column: C_OLCand.DatePromised_Override
-- 2025-09-30T14:56:05.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550520,754282,0,548442,145,TO_TIMESTAMP('2025-09-30 14:56:04.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Zugesagter Termin abw.',190,90,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:04.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:05.324Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754282 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:05.381Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542184)
;

-- 2025-09-30T14:56:05.445Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754282
;

-- 2025-09-30T14:56:05.502Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754282)
;

-- 2025-09-30T14:56:05.617Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754282
;

-- 2025-09-30T14:56:05.674Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754282, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554066
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Zugesagter Termin eff.
-- Column: C_OLCand.DatePromised_Effective
-- 2025-09-30T14:56:06.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550546,754283,0,548442,128,TO_TIMESTAMP('2025-09-30 14:56:05.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Zugesagter Termin eff.',200,100,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:05.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:06.597Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754283 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:06.656Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542422)
;

-- 2025-09-30T14:56:06.717Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754283
;

-- 2025-09-30T14:56:06.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754283)
;

-- 2025-09-30T14:56:06.887Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754283
;

-- 2025-09-30T14:56:06.943Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754283, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554080
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Geschäftspartner eff.
-- Column: C_OLCand.C_BPartner_Effective_ID
-- 2025-09-30T14:56:07.819Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550544,754284,0,548442,244,TO_TIMESTAMP('2025-09-30 14:56:07.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Geschäftspartner eff.',280,190,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:07.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:07.878Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754284 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:07.936Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542420)
;

-- 2025-09-30T14:56:07.999Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754284
;

-- 2025-09-30T14:56:08.059Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754284)
;

-- 2025-09-30T14:56:08.172Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754284
;

-- 2025-09-30T14:56:08.228Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754284, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554081
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Standort eff.
-- Column: C_OLCand.C_BP_Location_Effective_ID
-- 2025-09-30T14:56:09.100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550545,754285,0,548442,100,TO_TIMESTAMP('2025-09-30 14:56:08.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',0,'de.metas.ordercandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Standort eff.',290,200,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:08.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:09.155Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754285 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:09.214Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542421)
;

-- 2025-09-30T14:56:09.276Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754285
;

-- 2025-09-30T14:56:09.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754285)
;

-- 2025-09-30T14:56:09.447Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754285
;

-- 2025-09-30T14:56:09.504Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754285, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554082
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Preis eff.
-- Column: C_OLCand.PriceActual
-- 2025-09-30T14:56:10.323Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551501,754286,1000294,0,548442,77,TO_TIMESTAMP('2025-09-30 14:56:09.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Effektiver Preis',14,'de.metas.ordercandidate','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Preis eff.',460,130,1,1,TO_TIMESTAMP('2025-09-30 14:56:09.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:10.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754286 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:10.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000294)
;

-- 2025-09-30T14:56:10.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754286
;

-- 2025-09-30T14:56:10.560Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754286)
;

-- 2025-09-30T14:56:10.678Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754286
;

-- 2025-09-30T14:56:10.737Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754286, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555163
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Produkt abw.
-- Column: C_OLCand.M_Product_Override_ID
-- 2025-09-30T14:56:11.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551509,754287,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:10.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Produkt abw.',80,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:10.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:11.658Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754287 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:11.718Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542635)
;

-- 2025-09-30T14:56:11.781Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754287
;

-- 2025-09-30T14:56:11.838Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754287)
;

-- 2025-09-30T14:56:11.955Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754287
;

-- 2025-09-30T14:56:12.011Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754287, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555174
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Produkt eff.
-- Column: C_OLCand.M_Product_Effective_ID
-- 2025-09-30T14:56:12.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551510,754288,0,548442,280,TO_TIMESTAMP('2025-09-30 14:56:12.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Produkt eff.',90,40,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:12.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:12.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754288 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:13.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542636)
;

-- 2025-09-30T14:56:13.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754288
;

-- 2025-09-30T14:56:13.126Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754288)
;

-- 2025-09-30T14:56:13.239Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754288
;

-- 2025-09-30T14:56:13.296Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754288, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555175
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Preis int.
-- Column: C_OLCand.PriceInternal
-- 2025-09-30T14:56:14.189Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551511,754289,0,548442,62,TO_TIMESTAMP('2025-09-30 14:56:13.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Interner Preis laut Stammdaten',0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Preis int.',450,120,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:13.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:14.248Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754289 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:14.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542637)
;

-- 2025-09-30T14:56:14.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754289
;

-- 2025-09-30T14:56:14.429Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754289)
;

-- 2025-09-30T14:56:14.545Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754289
;

-- 2025-09-30T14:56:14.602Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754289, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555176
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Preiseinheit int.
-- Column: C_OLCand.Price_UOM_Internal_ID
-- 2025-09-30T14:56:15.805Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551512,754290,0,548442,52,TO_TIMESTAMP('2025-09-30 14:56:14.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Interne Preiseinheit laut Stammdaten',0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Preiseinheit int.',170,60,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:14.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:15.863Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:15.921Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542638)
;

-- 2025-09-30T14:56:15.980Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754290
;

-- 2025-09-30T14:56:16.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754290)
;

-- 2025-09-30T14:56:16.151Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754290
;

-- 2025-09-30T14:56:16.208Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754290, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555177
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Packvorschrift-Produkt Zuordnung abw.
-- Column: C_OLCand.M_HU_PI_Item_Product_Override_ID
-- 2025-09-30T14:56:17.004Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551513,754291,1001852,0,548442,230,TO_TIMESTAMP('2025-09-30 14:56:16.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Packvorschrift-Produkt Zuordnung abw.',110,210,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:16.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:17.062Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:17.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001852)
;

-- 2025-09-30T14:56:17.179Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754291
;

-- 2025-09-30T14:56:17.234Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754291)
;

-- 2025-09-30T14:56:17.351Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754291
;

-- 2025-09-30T14:56:17.408Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754291, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555178
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Packvorschrift-Produkt Zuordnung eff.
-- Column: C_OLCand.M_HU_PI_Item_Product_Effective_ID
-- 2025-09-30T14:56:18.299Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551514,754292,0,548442,222,TO_TIMESTAMP('2025-09-30 14:56:17.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Packvorschrift-Produkt Zuordnung eff.',120,220,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:17.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:18.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:18.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542639)
;

-- 2025-09-30T14:56:18.473Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754292
;

-- 2025-09-30T14:56:18.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754292)
;

-- 2025-09-30T14:56:18.650Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754292
;

-- 2025-09-30T14:56:18.707Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754292, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555179
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Maßeinheit int.
-- Column: C_OLCand.C_UOM_Internal_ID
-- 2025-09-30T14:56:19.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551515,754293,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:18.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Maßeinheit int.',160,0,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:18.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:19.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:19.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542640)
;

-- 2025-09-30T14:56:19.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754293
;

-- 2025-09-30T14:56:19.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754293)
;

-- 2025-09-30T14:56:19.919Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754293
;

-- 2025-09-30T14:56:19.976Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754293, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555183
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Preisdiff.
-- Column: C_OLCand.PriceDifference
-- 2025-09-30T14:56:20.808Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551517,754294,578640,0,548442,80,TO_TIMESTAMP('2025-09-30 14:56:20.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Preisdifferenz (imp. - int.)',0,'@PriceDifference/null@!=null','de.metas.ordercandidate','Preisdifferenz (imp. - int.)',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Preisdiff.',470,140,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:20.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:20.866Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754294 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:20.924Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578640)
;

-- 2025-09-30T14:56:20.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754294
;

-- 2025-09-30T14:56:21.044Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754294)
;

-- 2025-09-30T14:56:21.160Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754294
;

-- 2025-09-30T14:56:21.219Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754294, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555186
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Produktbeschreibung
-- Column: C_OLCand.ProductDescription
-- 2025-09-30T14:56:22.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552025,754295,0,548442,245,TO_TIMESTAMP('2025-09-30 14:56:21.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produktbeschreibung',255,'de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Produktbeschreibung',220,0,999,1,TO_TIMESTAMP('2025-09-30 14:56:21.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:22.141Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:22.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2651)
;

-- 2025-09-30T14:56:22.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754295
;

-- 2025-09-30T14:56:22.320Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754295)
;

-- 2025-09-30T14:56:22.437Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754295
;

-- 2025-09-30T14:56:22.496Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754295, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555762
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferempfänger abw.
-- Column: C_OLCand.DropShip_BPartner_Override_ID
-- 2025-09-30T14:56:23.387Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554406,754296,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:22.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lieferempfänger abw.',580,380,1,1,TO_TIMESTAMP('2025-09-30 14:56:22.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:23.445Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:23.503Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543084)
;

-- 2025-09-30T14:56:23.565Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754296
;

-- 2025-09-30T14:56:23.622Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754296)
;

-- 2025-09-30T14:56:23.740Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754296
;

-- 2025-09-30T14:56:23.802Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754296, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556939
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferadresse abw.
-- Column: C_OLCand.DropShip_Location_Override_ID
-- 2025-09-30T14:56:24.678Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554407,754297,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:23.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lieferadresse abw.',610,410,1,1,TO_TIMESTAMP('2025-09-30 14:56:23.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:24.736Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:24.794Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543085)
;

-- 2025-09-30T14:56:24.853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754297
;

-- 2025-09-30T14:56:24.908Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754297)
;

-- 2025-09-30T14:56:25.023Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754297
;

-- 2025-09-30T14:56:25.081Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754297, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556940
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferempfänger eff.
-- Column: C_OLCand.DropShip_BPartner_Effective_ID
-- 2025-09-30T14:56:25.958Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554409,754298,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:25.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lieferempfänger eff.',590,390,1,1,TO_TIMESTAMP('2025-09-30 14:56:25.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:26.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:26.073Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543087)
;

-- 2025-09-30T14:56:26.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754298
;

-- 2025-09-30T14:56:26.191Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754298)
;

-- 2025-09-30T14:56:26.311Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754298
;

-- 2025-09-30T14:56:26.368Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754298, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556941
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferadresse eff.
-- Column: C_OLCand.DropShip_Location_Effective_ID
-- 2025-09-30T14:56:27.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554410,754299,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:26.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lieferadresse eff.',620,420,1,1,TO_TIMESTAMP('2025-09-30 14:56:26.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:27.335Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:27.394Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543088)
;

-- 2025-09-30T14:56:27.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754299
;

-- 2025-09-30T14:56:27.514Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754299)
;

-- 2025-09-30T14:56:27.628Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754299
;

-- 2025-09-30T14:56:27.684Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754299, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556942
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabe-Partner abw.
-- Column: C_OLCand.HandOver_Partner_Override_ID
-- 2025-09-30T14:56:28.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554411,754300,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:27.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auswählbare Partner haben zum Käufer eine Partner-Beziehung mit "Abladeort=Ja".',10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Übergabe-Partner abw.',360,222,1,1,TO_TIMESTAMP('2025-09-30 14:56:27.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:28.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:28.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543089)
;

-- 2025-09-30T14:56:28.761Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754300
;

-- 2025-09-30T14:56:28.819Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754300)
;

-- 2025-09-30T14:56:28.934Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754300
;

-- 2025-09-30T14:56:28.991Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754300, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556943
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabe-Partner eff.
-- Column: C_OLCand.HandOver_Partner_Effective_ID
-- 2025-09-30T14:56:29.878Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554412,754301,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:29.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Übergabe-Partner eff.',370,223,1,1,TO_TIMESTAMP('2025-09-30 14:56:29.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:29.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:29.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543090)
;

-- 2025-09-30T14:56:30.053Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754301
;

-- 2025-09-30T14:56:30.110Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754301)
;

-- 2025-09-30T14:56:30.227Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754301
;

-- 2025-09-30T14:56:30.285Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754301, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556944
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabeadresse abw.
-- Column: C_OLCand.HandOver_Location_Override_ID
-- 2025-09-30T14:56:31.472Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554413,754302,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:30.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Übergabeadresse abw.',390,225,1,1,TO_TIMESTAMP('2025-09-30 14:56:30.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:31.531Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:31.593Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543091)
;

-- 2025-09-30T14:56:31.653Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754302
;

-- 2025-09-30T14:56:31.711Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754302)
;

-- 2025-09-30T14:56:31.828Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754302
;

-- 2025-09-30T14:56:31.886Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754302, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556945
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabeadresse eff.
-- Column: C_OLCand.HandOver_Location_Effective_ID
-- 2025-09-30T14:56:33.058Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554414,754303,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:31.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Übergabeadresse eff.',400,226,1,1,TO_TIMESTAMP('2025-09-30 14:56:31.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:33.116Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:33.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543092)
;

-- 2025-09-30T14:56:33.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754303
;

-- 2025-09-30T14:56:33.292Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754303)
;

-- 2025-09-30T14:56:33.409Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754303
;

-- 2025-09-30T14:56:33.466Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754303, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556946
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Manuelle Verpackungskapazität
-- Column: C_OLCand.IsManualQtyItemCapacity
-- 2025-09-30T14:56:34.385Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549992,754304,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:33.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt',1,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','Y','N','Manuelle Verpackungskapazität',630,1,1,TO_TIMESTAMP('2025-09-30 14:56:33.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:34.446Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:34.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542287)
;

-- 2025-09-30T14:56:34.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754304
;

-- 2025-09-30T14:56:34.628Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754304)
;

-- 2025-09-30T14:56:34.741Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754304
;

-- 2025-09-30T14:56:34.796Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754304, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569296
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Produkt-Preis
-- Column: C_OLCand.M_ProductPrice_ID
-- 2025-09-30T14:56:35.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552559,754305,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:34.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Produkt-Preis',640,1,1,TO_TIMESTAMP('2025-09-30 14:56:34.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:35.775Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:35.835Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500042)
;

-- 2025-09-30T14:56:35.898Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754305
;

-- 2025-09-30T14:56:35.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754305)
;

-- 2025-09-30T14:56:36.069Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754305
;

-- 2025-09-30T14:56:36.126Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754305, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569297
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Attribute price
-- Column: C_OLCand.M_ProductPrice_Attribute_ID
-- 2025-09-30T14:56:37.061Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552560,754306,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:36.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Attribute price',650,1,1,TO_TIMESTAMP('2025-09-30 14:56:36.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:37.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:37.186Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542201)
;

-- 2025-09-30T14:56:37.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754306
;

-- 2025-09-30T14:56:37.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754306)
;

-- 2025-09-30T14:56:37.416Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754306
;

-- 2025-09-30T14:56:37.473Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754306, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569298
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Explicit Product Price Attributes
-- Column: C_OLCand.IsExplicitProductPriceAttribute
-- 2025-09-30T14:56:38.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552561,754307,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:37.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Explicit Product Price Attributes',660,1,1,TO_TIMESTAMP('2025-09-30 14:56:37.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:38.465Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:38.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542855)
;

-- 2025-09-30T14:56:38.590Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754307
;

-- 2025-09-30T14:56:38.647Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754307)
;

-- 2025-09-30T14:56:38.765Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754307
;

-- 2025-09-30T14:56:38.820Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754307, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569299
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Externe Datensatz-Zeilen-ID
-- Column: C_OLCand.ExternalLineId
-- 2025-09-30T14:56:39.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559589,754308,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:38.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'@ExternalLineId/-1@>0','de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Externe Datensatz-Zeilen-ID',670,1,1,TO_TIMESTAMP('2025-09-30 14:56:38.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:39.739Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:39.797Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575914)
;

-- 2025-09-30T14:56:39.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754308
;

-- 2025-09-30T14:56:39.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754308)
;

-- 2025-09-30T14:56:40.031Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754308
;

-- 2025-09-30T14:56:40.088Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754308, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569300
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Vorbelegtes Rechnungsdatum
-- Column: C_OLCand.PresetDateInvoiced
-- 2025-09-30T14:56:41.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563247,754309,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:40.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Vorbelegtes Rechnungsdatum',680,1,1,TO_TIMESTAMP('2025-09-30 14:56:40.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:41.078Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:41.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577060)
;

-- 2025-09-30T14:56:41.191Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754309
;

-- 2025-09-30T14:56:41.247Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754309)
;

-- 2025-09-30T14:56:41.364Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754309
;

-- 2025-09-30T14:56:41.421Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754309, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569301
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rechnungs-Belegart
-- Column: C_OLCand.C_DocTypeInvoice_ID
-- 2025-09-30T14:56:42.348Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563248,754310,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:41.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document type used for invoices generated from this sales document',10,'de.metas.ordercandidate','The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.',0,'Y','N','N','N','N','N','N','N','N','N','N','Rechnungs-Belegart',690,1,1,TO_TIMESTAMP('2025-09-30 14:56:41.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:42.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:42.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1072)
;

-- 2025-09-30T14:56:42.521Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754310
;

-- 2025-09-30T14:56:42.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754310)
;

-- 2025-09-30T14:56:42.689Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754310
;

-- 2025-09-30T14:56:42.746Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754310, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569302
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Steuerkategorie
-- Column: C_OLCand.C_TaxCategory_ID
-- 2025-09-30T14:56:43.997Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563249,754311,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:42.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerkategorie',10,'de.metas.ordercandidate','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
',0,'Y','N','N','N','N','N','N','N','N','N','N','Steuerkategorie',700,1,1,TO_TIMESTAMP('2025-09-30 14:56:42.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:44.055Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:44.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(211)
;

-- 2025-09-30T14:56:44.170Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754311
;

-- 2025-09-30T14:56:44.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754311)
;

-- 2025-09-30T14:56:44.339Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754311
;

-- 2025-09-30T14:56:44.395Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754311, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 569303
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Externe Datensatz-Kopf-ID
-- Column: C_OLCand.ExternalHeaderId
-- 2025-09-30T14:56:45.273Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563706,754312,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:44.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'@ExternalHeaderId/-1@>0','de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Externe Datensatz-Kopf-ID',660,1,1,TO_TIMESTAMP('2025-09-30 14:56:44.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:45.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:45.387Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575915)
;

-- 2025-09-30T14:56:45.445Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754312
;

-- 2025-09-30T14:56:45.503Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754312)
;

-- 2025-09-30T14:56:45.618Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754312
;

-- 2025-09-30T14:56:45.675Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754312, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 570748
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Auftragsdatum
-- Column: C_OLCand.DateOrdered
-- 2025-09-30T14:56:46.624Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568733,754313,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:45.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags',7,'de.metas.ordercandidate','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftragsdatum',710,1,1,TO_TIMESTAMP('2025-09-30 14:56:45.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:46.685Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:46.743Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(268)
;

-- 2025-09-30T14:56:46.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754313
;

-- 2025-09-30T14:56:46.862Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754313)
;

-- 2025-09-30T14:56:46.978Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754313
;

-- 2025-09-30T14:56:47.037Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754313, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 583678
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Vorbelegtes Lieferdatum
-- Column: C_OLCand.PresetDateShipped
-- 2025-09-30T14:56:47.978Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568739,754314,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:47.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Vorbelegtes Lieferdatum',720,1,1,TO_TIMESTAMP('2025-09-30 14:56:47.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:48.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:48.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577062)
;

-- 2025-09-30T14:56:48.159Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754314
;

-- 2025-09-30T14:56:48.217Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754314)
;

-- 2025-09-30T14:56:48.332Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754314
;

-- 2025-09-30T14:56:48.389Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754314, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 586113
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Abr. Menge basiert auf
-- Column: C_OLCand.InvoicableQtyBasedOn
-- 2025-09-30T14:56:49.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568518,754315,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:48.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',11,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Abr. Menge basiert auf',730,1,1,TO_TIMESTAMP('2025-09-30 14:56:48.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:49.387Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:49.449Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948)
;

-- 2025-09-30T14:56:49.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754315
;

-- 2025-09-30T14:56:49.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754315)
;

-- 2025-09-30T14:56:49.701Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754315
;

-- 2025-09-30T14:56:49.759Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754315, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591807
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferweg
-- Column: C_OLCand.M_Shipper_ID
-- 2025-09-30T14:56:50.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569630,754316,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:49.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',10,'de.metas.ordercandidate','"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','N','N','N','N','N','N','N','N','N','N','Lieferweg',740,1,1,TO_TIMESTAMP('2025-09-30 14:56:49.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:50.763Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:50.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2025-09-30T14:56:50.884Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754316
;

-- 2025-09-30T14:56:50.943Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754316)
;

-- 2025-09-30T14:56:51.061Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754316
;

-- 2025-09-30T14:56:51.118Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754316, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591808
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Vertriebspartner
-- Column: C_OLCand.C_BPartner_SalesRep_ID
-- 2025-09-30T14:56:52.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569632,754317,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:51.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Vertriebspartner',750,1,1,TO_TIMESTAMP('2025-09-30 14:56:51.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:52.128Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:52.188Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357)
;

-- 2025-09-30T14:56:52.252Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754317
;

-- 2025-09-30T14:56:52.309Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754317)
;

-- 2025-09-30T14:56:52.423Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754317
;

-- 2025-09-30T14:56:52.480Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754317, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591809
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Zahlungsweise
-- Column: C_OLCand.PaymentRule
-- 2025-09-30T14:56:53.452Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569633,754318,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:52.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie die Rechnung bezahlt wird',250,'de.metas.ordercandidate','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',0,'Y','N','N','N','N','N','N','N','N','N','N','Zahlungsweise',760,1,1,TO_TIMESTAMP('2025-09-30 14:56:52.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:53.510Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:53.572Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143)
;

-- 2025-09-30T14:56:53.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754318
;

-- 2025-09-30T14:56:53.695Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754318)
;

-- 2025-09-30T14:56:53.815Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754318
;

-- 2025-09-30T14:56:53.873Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754318, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591810
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Auftrags-Belegart
-- Column: C_OLCand.C_DocTypeOrder_ID
-- 2025-09-30T14:56:54.813Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569635,754319,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:53.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document type used for the orders generated from this order candidate',10,'de.metas.ordercandidate','The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftrags-Belegart',770,1,1,TO_TIMESTAMP('2025-09-30 14:56:53.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:54.871Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:54.928Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577366)
;

-- 2025-09-30T14:56:54.988Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754319
;

-- 2025-09-30T14:56:55.045Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754319)
;

-- 2025-09-30T14:56:55.165Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754319
;

-- 2025-09-30T14:56:55.223Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754319, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591811
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferkontakt
-- Column: C_OLCand.DropShip_User_ID
-- 2025-09-30T14:56:56.105Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569886,754320,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:55.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lieferkontakt',690,1,1,TO_TIMESTAMP('2025-09-30 14:56:55.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:56.161Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:56.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53460)
;

-- 2025-09-30T14:56:56.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754320
;

-- 2025-09-30T14:56:56.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754320)
;

-- 2025-09-30T14:56:56.457Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754320
;

-- 2025-09-30T14:56:56.514Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754320, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 595931
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabe-Kontakt
-- Column: C_OLCand.HandOver_User_ID
-- 2025-09-30T14:56:57.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569887,754321,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:56.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Übergabe-Kontakt',680,1,1,TO_TIMESTAMP('2025-09-30 14:56:56.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:57.430Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:57.488Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577442)
;

-- 2025-09-30T14:56:57.547Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754321
;

-- 2025-09-30T14:56:57.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754321)
;

-- 2025-09-30T14:56:57.720Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754321
;

-- 2025-09-30T14:56:57.778Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754321, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 595932
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Zahlungsbedingung
-- Column: C_OLCand.C_PaymentTerm_ID
-- 2025-09-30T14:56:58.656Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570815,754322,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:57.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Bedingungen für die Bezahlung dieses Vorgangs',0,'de.metas.ordercandidate','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zahlungsbedingung',630,430,0,1,1,TO_TIMESTAMP('2025-09-30 14:56:57.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:56:58.715Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:56:58.771Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204)
;

-- 2025-09-30T14:56:58.831Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754322
;

-- 2025-09-30T14:56:58.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754322)
;

-- 2025-09-30T14:56:59.002Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754322
;

-- 2025-09-30T14:56:59.058Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754322, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 613713
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lager
-- Column: C_OLCand.M_Warehouse_ID
-- 2025-09-30T14:56:59.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572302,754323,0,548442,0,TO_TIMESTAMP('2025-09-30 14:56:59.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'de.metas.ordercandidate','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Lager',780,1,1,TO_TIMESTAMP('2025-09-30 14:56:59.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:00.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:00.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-09-30T14:57:00.176Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754323
;

-- 2025-09-30T14:57:00.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754323)
;

-- 2025-09-30T14:57:00.348Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754323
;

-- 2025-09-30T14:57:00.405Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754323, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627723
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Gruppenrabatt Zeile
-- Column: C_OLCand.IsGroupCompensationLine
-- 2025-09-30T14:57:01.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573256,754324,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:00.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Gruppenrabatt Zeile',790,1,1,TO_TIMESTAMP('2025-09-30 14:57:00.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:01.386Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:01.443Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543458)
;

-- 2025-09-30T14:57:01.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754324
;

-- 2025-09-30T14:57:01.566Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754324)
;

-- 2025-09-30T14:57:01.684Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754324
;

-- 2025-09-30T14:57:01.739Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754324, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 639797
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Gruppierungs-Schlüssel
-- Column: C_OLCand.CompensationGroupKey
-- 2025-09-30T14:57:02.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573257,754325,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:01.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1024,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Gruppierungs-Schlüssel',800,1,1,TO_TIMESTAMP('2025-09-30 14:57:01.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:02.730Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:02.788Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578911)
;

-- 2025-09-30T14:57:02.851Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754325
;

-- 2025-09-30T14:57:02.909Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754325)
;

-- 2025-09-30T14:57:03.024Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754325
;

-- 2025-09-30T14:57:03.080Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754325, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 639798
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Gruppierungsfehler
-- Column: C_OLCand.IsGroupingError
-- 2025-09-30T14:57:03.992Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573342,754326,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:03.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Gruppierungsfehler',640,440,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:03.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:04.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:04.110Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579002)
;

-- 2025-09-30T14:57:04.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754326
;

-- 2025-09-30T14:57:04.232Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754326)
;

-- 2025-09-30T14:57:04.350Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754326
;

-- 2025-09-30T14:57:04.408Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754326, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 642616
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Gruppierungsfehlermeldung
-- Column: C_OLCand.GroupingErrorMessage
-- 2025-09-30T14:57:05.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573343,754327,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:04.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@IsGroupingError/''N''@=''Y''','de.metas.ordercandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Gruppierungsfehlermeldung',650,450,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:04.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:05.328Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:05.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579003)
;

-- 2025-09-30T14:57:05.448Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754327
;

-- 2025-09-30T14:57:05.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754327)
;

-- 2025-09-30T14:57:05.622Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754327
;

-- 2025-09-30T14:57:05.678Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754327, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 642617
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Import warning message
-- Column: C_OLCand.ImportWarningMessage
-- 2025-09-30T14:57:06.609Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573509,754328,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:05.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Import warning message',810,1,1,TO_TIMESTAMP('2025-09-30 14:57:05.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:06.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:06.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579022)
;

-- 2025-09-30T14:57:06.794Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754328
;

-- 2025-09-30T14:57:06.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754328)
;

-- 2025-09-30T14:57:06.964Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754328
;

-- 2025-09-30T14:57:07.021Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754328, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660978
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Standort (Address)
-- Column: C_OLCand.C_BPartner_Location_Value_ID
-- 2025-09-30T14:57:07.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573518,754329,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:07.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.ordercandidate','',0,'Y','N','N','N','N','N','N','N','N','N','N','Standort (Address)',820,1,1,TO_TIMESTAMP('2025-09-30 14:57:07.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:08.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:08.058Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023)
;

-- 2025-09-30T14:57:08.122Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754329
;

-- 2025-09-30T14:57:08.178Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754329)
;

-- 2025-09-30T14:57:08.294Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754329
;

-- 2025-09-30T14:57:08.350Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754329, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660979
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rechnungsstandort (Address)
-- Column: C_OLCand.Bill_Location_Value_ID
-- 2025-09-30T14:57:09.291Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573519,754330,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:08.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Rechnungsstandort (Address)',830,1,1,TO_TIMESTAMP('2025-09-30 14:57:08.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:09.349Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:09.407Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579024)
;

-- 2025-09-30T14:57:09.465Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754330
;

-- 2025-09-30T14:57:09.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754330)
;

-- 2025-09-30T14:57:09.634Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754330
;

-- 2025-09-30T14:57:09.691Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754330, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660980
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferadresse (Address)
-- Column: C_OLCand.DropShip_Location_Value_ID
-- 2025-09-30T14:57:10.616Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573520,754331,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:09.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner Location for shipping to',10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Lieferadresse (Address)',840,1,1,TO_TIMESTAMP('2025-09-30 14:57:09.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:10.674Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:10.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579025)
;

-- 2025-09-30T14:57:10.799Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754331
;

-- 2025-09-30T14:57:10.855Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754331)
;

-- 2025-09-30T14:57:10.971Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754331
;

-- 2025-09-30T14:57:11.029Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754331, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660981
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabeadresse (Address)
-- Column: C_OLCand.HandOver_Location_Value_ID
-- 2025-09-30T14:57:11.962Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573521,754332,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:11.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Übergabeadresse (Address)',850,1,1,TO_TIMESTAMP('2025-09-30 14:57:11.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:12.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:12.078Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579026)
;

-- 2025-09-30T14:57:12.138Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754332
;

-- 2025-09-30T14:57:12.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754332)
;

-- 2025-09-30T14:57:12.311Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754332
;

-- 2025-09-30T14:57:12.368Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754332, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660982
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Standort abw.
-- Column: C_OLCand.C_BP_Location_Override_Value_ID
-- 2025-09-30T14:57:13.284Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573522,754333,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:12.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.ordercandidate','',0,'Y','N','N','N','N','N','N','N','N','N','N','Standort abw.',860,1,1,TO_TIMESTAMP('2025-09-30 14:57:12.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:13.340Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:13.400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579027)
;

-- 2025-09-30T14:57:13.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754333
;

-- 2025-09-30T14:57:13.521Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754333)
;

-- 2025-09-30T14:57:13.638Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754333
;

-- 2025-09-30T14:57:13.696Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754333, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660983
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Lieferadresse abw.
-- Column: C_OLCand.DropShip_Location_Override_Value_ID
-- 2025-09-30T14:57:14.628Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573523,754334,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:13.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Lieferadresse abw.',870,1,1,TO_TIMESTAMP('2025-09-30 14:57:13.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:14.688Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:14.747Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579028)
;

-- 2025-09-30T14:57:14.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754334
;

-- 2025-09-30T14:57:14.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754334)
;

-- 2025-09-30T14:57:14.984Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754334
;

-- 2025-09-30T14:57:15.041Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754334, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660984
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Übergabeadresse abw.
-- Column: C_OLCand.HandOver_Location_Override_Value_ID
-- 2025-09-30T14:57:15.976Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573524,754335,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:15.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Übergabeadresse abw.',880,1,1,TO_TIMESTAMP('2025-09-30 14:57:15.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:16.035Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:16.095Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579029)
;

-- 2025-09-30T14:57:16.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754335
;

-- 2025-09-30T14:57:16.213Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754335)
;

-- 2025-09-30T14:57:16.326Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754335
;

-- 2025-09-30T14:57:16.382Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754335, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660985
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Vertriebpartner aus
-- Column: C_OLCand.ApplySalesRepFrom
-- 2025-09-30T14:57:17.305Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577133,754336,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:16.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Vertriebpartner aus',890,1,1,TO_TIMESTAMP('2025-09-30 14:57:16.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:17.363Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:17.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579911)
;

-- 2025-09-30T14:57:17.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754336
;

-- 2025-09-30T14:57:17.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754336)
;

-- 2025-09-30T14:57:17.657Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754336
;

-- 2025-09-30T14:57:17.715Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754336, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660986
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Vertriebpartner int.
-- Column: C_OLCand.C_BPartner_SalesRep_Internal_ID
-- 2025-09-30T14:57:18.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577134,754337,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:17.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Vertriebpartner int.',900,1,1,TO_TIMESTAMP('2025-09-30 14:57:17.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:18.693Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:18.751Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579912)
;

-- 2025-09-30T14:57:18.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754337
;

-- 2025-09-30T14:57:18.869Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754337)
;

-- 2025-09-30T14:57:18.984Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754337
;

-- 2025-09-30T14:57:19.041Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754337, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 660987
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Name Geschäftspartner
-- Column: C_OLCand.BPartnerName
-- 2025-09-30T14:57:19.998Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578889,754338,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:19.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Name Geschäftspartner',910,1,1,TO_TIMESTAMP('2025-09-30 14:57:19.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:20.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:20.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350)
;

-- 2025-09-30T14:57:20.178Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754338
;

-- 2025-09-30T14:57:20.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754338)
;

-- 2025-09-30T14:57:20.348Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754338
;

-- 2025-09-30T14:57:20.407Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754338, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 675194
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> eMail
-- Column: C_OLCand.EMail
-- 2025-09-30T14:57:21.346Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578891,754339,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:20.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse',200,'de.metas.ordercandidate','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.',0,'Y','N','N','N','N','N','N','N','N','N','N','eMail',920,1,1,TO_TIMESTAMP('2025-09-30 14:57:20.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:21.405Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:21.466Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881)
;

-- 2025-09-30T14:57:21.533Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754339
;

-- 2025-09-30T14:57:21.591Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754339)
;

-- 2025-09-30T14:57:21.708Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754339
;

-- 2025-09-30T14:57:21.766Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754339, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 675195
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Telefon
-- Column: C_OLCand.Phone
-- 2025-09-30T14:57:22.706Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578890,754340,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:21.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beschreibt eine Telefon Nummer',40,'de.metas.ordercandidate','',0,'Y','N','N','N','N','N','N','N','N','N','N','Telefon',930,1,1,TO_TIMESTAMP('2025-09-30 14:57:21.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:22.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754340 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:22.824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505)
;

-- 2025-09-30T14:57:22.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754340
;

-- 2025-09-30T14:57:22.943Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754340)
;

-- 2025-09-30T14:57:23.061Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754340
;

-- 2025-09-30T14:57:23.120Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754340, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 675196
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Async Batch
-- Column: C_OLCand.C_Async_Batch_ID
-- 2025-09-30T14:57:24.076Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575254,754341,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:23.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Async Batch',940,1,1,TO_TIMESTAMP('2025-09-30 14:57:23.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:24.135Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:24.195Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542581)
;

-- 2025-09-30T14:57:24.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754341
;

-- 2025-09-30T14:57:24.318Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754341)
;

-- 2025-09-30T14:57:24.437Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754341
;

-- 2025-09-30T14:57:24.495Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754341, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 691399
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Group compensation discount percentage
-- Column: C_OLCand.GroupCompensationDiscountPercentage
-- 2025-09-30T14:57:25.449Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575256,754342,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:24.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Group compensation discount percentage',950,1,1,TO_TIMESTAMP('2025-09-30 14:57:24.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:25.508Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:25.566Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579536)
;

-- 2025-09-30T14:57:25.629Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754342
;

-- 2025-09-30T14:57:25.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754342)
;

-- 2025-09-30T14:57:25.805Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754342
;

-- 2025-09-30T14:57:25.863Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754342, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 691400
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Qty Shipped
-- Column: C_OLCand.QtyShipped
-- 2025-09-30T14:57:26.795Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575351,754343,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:25.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Qty Shipped',960,1,1,TO_TIMESTAMP('2025-09-30 14:57:25.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:26.854Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:26.912Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579558)
;

-- 2025-09-30T14:57:26.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754343
;

-- 2025-09-30T14:57:27.026Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754343)
;

-- 2025-09-30T14:57:27.139Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754343
;

-- 2025-09-30T14:57:27.196Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754343, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 691401
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Verpackungskapazität int.
-- Column: C_OLCand.QtyItemCapacityInternal
-- 2025-09-30T14:57:28.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582457,754344,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:27.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'In den metasfresh Stammdaten hinterlegte Verpackungskapazität',10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Verpackungskapazität int.',970,1,1,TO_TIMESTAMP('2025-09-30 14:57:27.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:28.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:28.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580710)
;

-- 2025-09-30T14:57:28.290Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754344
;

-- 2025-09-30T14:57:28.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754344)
;

-- 2025-09-30T14:57:28.461Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754344
;

-- 2025-09-30T14:57:28.519Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754344, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 691402
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> EDI-Import abgeschlossen
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2025-09-30T14:57:29.449Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585608,754345,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:28.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'@EXP_ReplicationTrx_ID/0@ > 0','de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','EDI-Import abgeschlossen',980,1,1,TO_TIMESTAMP('2025-09-30 14:57:28.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:29.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:29.563Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581955)
;

-- 2025-09-30T14:57:29.626Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754345
;

-- 2025-09-30T14:57:29.682Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754345)
;

-- 2025-09-30T14:57:29.794Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754345
;

-- 2025-09-30T14:57:29.851Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754345, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 710528
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> EDI-Importfehler
-- Column: C_OLCand.IsReplicationTrxError
-- 2025-09-30T14:57:30.787Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585612,754346,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:29.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'@EXP_ReplicationTrx_ID/0@ > 0','de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','EDI-Importfehler',990,1,1,TO_TIMESTAMP('2025-09-30 14:57:29.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:30.846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:30.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581956)
;

-- 2025-09-30T14:57:30.960Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754346
;

-- 2025-09-30T14:57:31.018Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754346)
;

-- 2025-09-30T14:57:31.135Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754346
;

-- 2025-09-30T14:57:31.192Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754346, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 710532
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> EDI-Import-Fehlermeldung
-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2025-09-30T14:57:32.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585613,754347,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:31.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'@IsReplicationTrxError/N@=''Y''','de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','EDI-Import-Fehlermeldung',1000,1,1,TO_TIMESTAMP('2025-09-30 14:57:31.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:32.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754347 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:32.230Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581957)
;

-- 2025-09-30T14:57:32.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754347
;

-- 2025-09-30T14:57:32.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754347)
;

-- 2025-09-30T14:57:32.467Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754347
;

-- 2025-09-30T14:57:32.523Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754347, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 710533
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- 2025-09-30T14:57:33.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588627,754348,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:32.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Menge Abw.',1010,1,1,TO_TIMESTAMP('2025-09-30 14:57:32.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:33.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:33.564Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583153)
;

-- 2025-09-30T14:57:33.625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754348
;

-- 2025-09-30T14:57:33.683Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754348)
;

-- 2025-09-30T14:57:33.797Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754348
;

-- 2025-09-30T14:57:33.854Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754348, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729006
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- 2025-09-30T14:57:34.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589169,754349,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:33.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.',1,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Rechnungsstellung',1020,1,1,TO_TIMESTAMP('2025-09-30 14:57:33.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:34.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:34.888Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(559)
;

-- 2025-09-30T14:57:34.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754349
;

-- 2025-09-30T14:57:35.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754349)
;

-- 2025-09-30T14:57:35.131Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754349
;

-- 2025-09-30T14:57:35.188Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754349, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 731785
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- 2025-09-30T14:57:36.108Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589171,754350,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:35.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Shipped Weight',1030,1,1,TO_TIMESTAMP('2025-09-30 14:57:35.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:36.164Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:36.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583290)
;

-- 2025-09-30T14:57:36.286Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754350
;

-- 2025-09-30T14:57:36.343Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754350)
;

-- 2025-09-30T14:57:36.458Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754350
;

-- 2025-09-30T14:57:36.515Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754350, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 731787
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- 2025-09-30T14:57:37.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589172,754351,0,548442,0,TO_TIMESTAMP('2025-09-30 14:57:36.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Shipped Weight UOM',1040,1,1,TO_TIMESTAMP('2025-09-30 14:57:36.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:37.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:37.548Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583291)
;

-- 2025-09-30T14:57:37.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754351
;

-- 2025-09-30T14:57:37.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754351)
;

-- 2025-09-30T14:57:37.785Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754351
;

-- 2025-09-30T14:57:37.844Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754351, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 731788
;

-- Tab: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile
-- Table: C_Order_Line_Alloc
-- 2025-09-30T14:57:38.727Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,547708,573700,0,548443,540417,541952,'Y',TO_TIMESTAMP('2025-09-30 14:57:37.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ordercandidate','N','N','A','C_Order_Line_Alloc','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Zuordnung - Auftragszeile','N',20,1,TO_TIMESTAMP('2025-09-30 14:57:37.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:38.785Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548443 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-09-30T14:57:38.844Z
/* DDL */  select update_tab_translation_from_ad_element(573700)
;

-- 2025-09-30T14:57:38.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548443)
;

-- 2025-09-30T14:57:39.021Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548443
;

-- 2025-09-30T14:57:39.076Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548443, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540452
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Aktiv
-- Column: C_Order_Line_Alloc.IsActive
-- 2025-09-30T14:57:40.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547703,754352,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:39.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.ordercandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',10,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:39.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:40.547Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:40.612Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-09-30T14:57:40.770Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754352
;

-- 2025-09-30T14:57:40.827Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754352)
;

-- 2025-09-30T14:57:40.943Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754352
;

-- 2025-09-30T14:57:41Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754352, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551014
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Aktualisiert
-- Column: C_Order_Line_Alloc.Updated
-- 2025-09-30T14:57:41.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547704,754353,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:41.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.ordercandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',20,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:41.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:42.011Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:42.078Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-09-30T14:57:42.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754353
;

-- 2025-09-30T14:57:42.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754353)
;

-- 2025-09-30T14:57:42.366Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754353
;

-- 2025-09-30T14:57:42.426Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754353, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551015
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Aktualisiert durch
-- Column: C_Order_Line_Alloc.UpdatedBy
-- 2025-09-30T14:57:43.391Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547705,754354,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:42.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.ordercandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',30,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:42.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:43.452Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:43.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-09-30T14:57:43.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754354
;

-- 2025-09-30T14:57:43.672Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754354)
;

-- 2025-09-30T14:57:43.788Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754354
;

-- 2025-09-30T14:57:43.844Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754354, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551016
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Auftragskandidat
-- Column: C_Order_Line_Alloc.C_OLCand_ID
-- 2025-09-30T14:57:44.741Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547708,754355,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:43.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftragskandidat',10,10,1,1,TO_TIMESTAMP('2025-09-30 14:57:43.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:44.801Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:44.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541148)
;

-- 2025-09-30T14:57:44.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754355
;

-- 2025-09-30T14:57:44.983Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754355)
;

-- 2025-09-30T14:57:45.102Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754355
;

-- 2025-09-30T14:57:45.158Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754355, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551017
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Auftragskandidat - Auftragszeile
-- Column: C_Order_Line_Alloc.C_Order_Line_Alloc_ID
-- 2025-09-30T14:57:46.088Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547706,754356,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:45.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftragskandidat - Auftragszeile',40,1,1,TO_TIMESTAMP('2025-09-30 14:57:45.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:46.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:46.205Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541868)
;

-- 2025-09-30T14:57:46.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754356
;

-- 2025-09-30T14:57:46.320Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754356)
;

-- 2025-09-30T14:57:46.437Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754356
;

-- 2025-09-30T14:57:46.495Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754356, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551018
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Auftragskand. Verarb.
-- Column: C_Order_Line_Alloc.C_OLCandProcessor_ID
-- 2025-09-30T14:57:47.369Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547711,754357,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:46.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Auftragskand. Verarb.',50,50,1,1,TO_TIMESTAMP('2025-09-30 14:57:46.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:47.427Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:47.487Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541149)
;

-- 2025-09-30T14:57:47.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754357
;

-- 2025-09-30T14:57:47.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754357)
;

-- 2025-09-30T14:57:47.724Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754357
;

-- 2025-09-30T14:57:47.780Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754357, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551019
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Auftragsposition
-- Column: C_Order_Line_Alloc.C_OrderLine_ID
-- 2025-09-30T14:57:48.685Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547707,754358,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:47.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'de.metas.ordercandidate','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Auftragsposition',20,20,1,1,TO_TIMESTAMP('2025-09-30 14:57:47.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:48.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:48.814Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561)
;

-- 2025-09-30T14:57:48.878Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754358
;

-- 2025-09-30T14:57:48.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754358)
;

-- 2025-09-30T14:57:49.053Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754358
;

-- 2025-09-30T14:57:49.116Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754358, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551020
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Belegstatus
-- Column: C_Order_Line_Alloc.DocStatus
-- 2025-09-30T14:57:49.960Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547710,754359,1002389,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:49.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document',2,'de.metas.ordercandidate','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Belegstatus',40,40,1,1,TO_TIMESTAMP('2025-09-30 14:57:49.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:50.032Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:50.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002389)
;

-- 2025-09-30T14:57:50.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754359
;

-- 2025-09-30T14:57:50.213Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754359)
;

-- 2025-09-30T14:57:50.328Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754359
;

-- 2025-09-30T14:57:50.385Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754359, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551021
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Bestellt/ Beauftragt
-- Column: C_Order_Line_Alloc.QtyOrdered
-- 2025-09-30T14:57:51.218Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547709,754360,1001686,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:50.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt',14,'de.metas.ordercandidate','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Bestellt/ Beauftragt',30,30,1,1,TO_TIMESTAMP('2025-09-30 14:57:50.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:51.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:51.335Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001686)
;

-- 2025-09-30T14:57:51.399Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754360
;

-- 2025-09-30T14:57:51.458Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754360)
;

-- 2025-09-30T14:57:51.577Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754360
;

-- 2025-09-30T14:57:51.634Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754360, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551022
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Erstellt
-- Column: C_Order_Line_Alloc.Created
-- 2025-09-30T14:57:52.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547701,754361,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:51.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.ordercandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt',60,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:51.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:52.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754361 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:52.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-09-30T14:57:52.796Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754361
;

-- 2025-09-30T14:57:52.853Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754361)
;

-- 2025-09-30T14:57:52.967Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754361
;

-- 2025-09-30T14:57:53.024Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754361, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551023
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Erstellt durch
-- Column: C_Order_Line_Alloc.CreatedBy
-- 2025-09-30T14:57:53.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547702,754362,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:53.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.ordercandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',70,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:53.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:54.046Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:54.118Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-09-30T14:57:54.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754362
;

-- 2025-09-30T14:57:54.272Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754362)
;

-- 2025-09-30T14:57:54.386Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754362
;

-- 2025-09-30T14:57:54.443Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754362, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551024
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Mandant
-- Column: C_Order_Line_Alloc.AD_Client_ID
-- 2025-09-30T14:57:55.431Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547699,754363,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:54.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.ordercandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',80,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:54.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:55.500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:55.558Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-09-30T14:57:55.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754363
;

-- 2025-09-30T14:57:55.768Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754363)
;

-- 2025-09-30T14:57:55.907Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754363
;

-- 2025-09-30T14:57:55.971Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754363, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551025
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> Sektion
-- Column: C_Order_Line_Alloc.AD_Org_ID
-- 2025-09-30T14:57:56.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,547700,754364,0,548443,0,TO_TIMESTAMP('2025-09-30 14:57:56.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.ordercandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Sektion',90,0,1,1,TO_TIMESTAMP('2025-09-30 14:57:56.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:57:57.055Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-30T14:57:57.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-09-30T14:57:57.270Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754364
;

-- 2025-09-30T14:57:57.328Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754364)
;

-- 2025-09-30T14:57:57.443Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754364
;

-- 2025-09-30T14:57:57.509Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754364, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 551026
;

-- Tab: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate)
-- UI Section: main
-- 2025-09-30T14:57:58.285Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548442,546964,TO_TIMESTAMP('2025-09-30 14:57:57.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-30 14:57:57.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-30T14:57:58.342Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546964 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-09-30T14:57:58.457Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546964
;

-- 2025-09-30T14:57:58.516Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546964, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540405
;

-- UI Section: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main
-- UI Column: 10
-- 2025-09-30T14:57:59.257Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548482,546964,TO_TIMESTAMP('2025-09-30 14:57:58.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-30 14:57:58.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10
-- UI Element Group: default
-- 2025-09-30T14:58:00.067Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548482,553556,TO_TIMESTAMP('2025-09-30 14:57:59.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-09-30 14:57:59.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.Kand.-Datum
-- Column: C_OLCand.DateCandidate
-- 2025-09-30T14:58:01.318Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754237,0,548442,553556,637319,'F',TO_TIMESTAMP('2025-09-30 14:58:00.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Kand.-Datum',10,20,10,TO_TIMESTAMP('2025-09-30 14:58:00.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.Daten Ziel
-- Column: C_OLCand.AD_DataDestination_ID
-- 2025-09-30T14:58:02.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754266,0,548442,553556,637320,'F',TO_TIMESTAMP('2025-09-30 14:58:01.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Daten Ziel',30,0,0,TO_TIMESTAMP('2025-09-30 14:58:01.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.ExternalHeaderId
-- Column: C_OLCand.ExternalHeaderId
-- 2025-09-30T14:58:03.495Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754312,0,548442,553556,637321,'F',TO_TIMESTAMP('2025-09-30 14:58:02.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'ExternalHeaderId',40,0,0,TO_TIMESTAMP('2025-09-30 14:58:02.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.ExternalLineId
-- Column: C_OLCand.ExternalLineId
-- 2025-09-30T14:58:04.783Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754308,0,548442,553556,637322,'F',TO_TIMESTAMP('2025-09-30 14:58:03.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'ExternalLineId',50,0,0,TO_TIMESTAMP('2025-09-30 14:58:03.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.EXP_ReplicationTrx_ID
-- Column: C_OLCand.EXP_ReplicationTrx_ID
-- 2025-09-30T14:58:05.856Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754279,0,548442,553556,637323,'F',TO_TIMESTAMP('2025-09-30 14:58:05.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','EXP_ReplicationTrx_ID',60,10,0,TO_TIMESTAMP('2025-09-30 14:58:05.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.QtyEntered
-- Column: C_OLCand.QtyEntered
-- 2025-09-30T14:58:06.919Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754241,0,548442,553556,637324,'F',TO_TIMESTAMP('2025-09-30 14:58:06.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','QtyEntered',80,70,0,TO_TIMESTAMP('2025-09-30 14:58:06.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- 2025-09-30T14:58:08.030Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754348,0,548442,553556,637325,'F',TO_TIMESTAMP('2025-09-30 14:58:07.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Menge Abw.',85,0,0,TO_TIMESTAMP('2025-09-30 14:58:07.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.C_UOM_ID
-- Column: C_OLCand.C_UOM_ID
-- 2025-09-30T14:58:09.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754240,0,548442,553556,637326,'F',TO_TIMESTAMP('2025-09-30 14:58:08.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','C_UOM_ID',90,0,0,TO_TIMESTAMP('2025-09-30 14:58:08.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10
-- UI Element Group: price
-- 2025-09-30T14:58:09.880Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548482,553557,TO_TIMESTAMP('2025-09-30 14:58:09.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','price',20,TO_TIMESTAMP('2025-09-30 14:58:09.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> price.Preis eff.
-- Column: C_OLCand.PriceActual
-- 2025-09-30T14:58:11.086Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754286,0,548442,553557,637327,'F',TO_TIMESTAMP('2025-09-30 14:58:10.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Preis eff.',30,80,0,TO_TIMESTAMP('2025-09-30 14:58:10.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> price.Preiseinheit
-- Column: C_OLCand.Price_UOM_Internal_ID
-- 2025-09-30T14:58:12.151Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754290,0,548442,553557,637328,'F',TO_TIMESTAMP('2025-09-30 14:58:11.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Preiseinheit',40,0,0,TO_TIMESTAMP('2025-09-30 14:58:11.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> price.PriceDifference
-- Column: C_OLCand.PriceDifference
-- 2025-09-30T14:58:13.249Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754294,0,548442,553557,637329,'F',TO_TIMESTAMP('2025-09-30 14:58:12.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','PriceDifference',50,100,0,TO_TIMESTAMP('2025-09-30 14:58:12.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Section: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main
-- UI Column: 20
-- 2025-09-30T14:58:14.020Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548483,546964,TO_TIMESTAMP('2025-09-30 14:58:13.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-30 14:58:13.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20
-- UI Element Group: flags
-- 2025-09-30T14:58:14.783Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548483,553558,TO_TIMESTAMP('2025-09-30 14:58:14.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-09-30 14:58:14.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Eintrag übernehmen
-- Column: C_OLCand.IsActive
-- 2025-09-30T14:58:15.985Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754227,0,548442,553558,637330,'F',TO_TIMESTAMP('2025-09-30 14:58:15.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Eintrag übernehmen',10,0,0,TO_TIMESTAMP('2025-09-30 14:58:15.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: C_OLCand.Processed
-- 2025-09-30T14:58:17.056Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754247,0,548442,553558,637331,'F',TO_TIMESTAMP('2025-09-30 14:58:16.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Verarbeitet',20,150,40,TO_TIMESTAMP('2025-09-30 14:58:16.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Fehler
-- Column: C_OLCand.IsError
-- 2025-09-30T14:58:18.139Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754249,0,548442,553558,637332,'F',TO_TIMESTAMP('2025-09-30 14:58:17.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Fehler',30,130,0,TO_TIMESTAMP('2025-09-30 14:58:17.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Referenz
-- Column: C_OLCand.POReference
-- 2025-09-30T14:58:19.255Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754270,0,548442,553558,637333,'F',TO_TIMESTAMP('2025-09-30 14:58:18.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Referenz',50,0,0,TO_TIMESTAMP('2025-09-30 14:58:18.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Währung
-- Column: C_OLCand.C_Currency_ID
-- 2025-09-30T14:58:20.344Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754260,0,548442,553558,637334,'F',TO_TIMESTAMP('2025-09-30 14:58:19.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Währung',70,90,0,TO_TIMESTAMP('2025-09-30 14:58:19.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Gruppierungsfehler
-- Column: C_OLCand.IsGroupingError
-- 2025-09-30T14:58:21.441Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754326,0,548442,553558,637335,'F',TO_TIMESTAMP('2025-09-30 14:58:20.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Gruppierungsfehler',80,140,0,TO_TIMESTAMP('2025-09-30 14:58:20.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Gruppierungsfehlermeldung
-- Column: C_OLCand.GroupingErrorMessage
-- 2025-09-30T14:58:22.522Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754327,0,548442,553558,637336,'F',TO_TIMESTAMP('2025-09-30 14:58:21.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Gruppierungsfehlermeldung',90,0,0,TO_TIMESTAMP('2025-09-30 14:58:21.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20
-- UI Element Group: replication trx
-- 2025-09-30T14:58:23.288Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548483,553559,TO_TIMESTAMP('2025-09-30 14:58:22.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','replication trx',20,TO_TIMESTAMP('2025-09-30 14:58:22.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2025-09-30T14:58:24.517Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754345,0,548442,553559,637337,'F',TO_TIMESTAMP('2025-09-30 14:58:23.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Replication transaction finished',10,160,0,TO_TIMESTAMP('2025-09-30 14:58:23.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication trx error
-- Column: C_OLCand.IsReplicationTrxError
-- 2025-09-30T14:58:25.685Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754346,0,548442,553559,637338,'F',TO_TIMESTAMP('2025-09-30 14:58:24.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Replication trx error',20,170,0,TO_TIMESTAMP('2025-09-30 14:58:24.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication trx error message
-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2025-09-30T14:58:26.771Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754347,0,548442,553559,637339,'F',TO_TIMESTAMP('2025-09-30 14:58:25.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Replication trx error message',30,0,0,TO_TIMESTAMP('2025-09-30 14:58:25.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate)
-- UI Section: sub
-- 2025-09-30T14:58:27.563Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548442,546965,TO_TIMESTAMP('2025-09-30 14:58:27.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-30 14:58:27.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'sub')
;

-- 2025-09-30T14:58:27.619Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546965 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-09-30T14:58:27.741Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546965
;

-- 2025-09-30T14:58:27.802Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546965, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540490
;

-- UI Section: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub
-- UI Column: 10
-- 2025-09-30T14:58:28.524Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548484,546965,TO_TIMESTAMP('2025-09-30 14:58:27.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-30 14:58:27.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10
-- UI Element Group: read
-- 2025-09-30T14:58:29.290Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548484,553560,TO_TIMESTAMP('2025-09-30 14:58:28.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','read',10,TO_TIMESTAMP('2025-09-30 14:58:28.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> read.Produkt eff.
-- Column: C_OLCand.M_Product_Effective_ID
-- 2025-09-30T14:58:30.469Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754288,0,548442,553560,637340,'F',TO_TIMESTAMP('2025-09-30 14:58:29.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Produkt eff.',10,60,0,TO_TIMESTAMP('2025-09-30 14:58:29.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-09-30T14:58:31.630Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754292,0,542147,637340,TO_TIMESTAMP('2025-09-30 14:58:30.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:30.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> read.C_BPartner_Effective_ID
-- Column: C_OLCand.C_BPartner_Effective_ID
-- 2025-09-30T14:58:32.591Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754284,0,548442,553560,637341,'F',TO_TIMESTAMP('2025-09-30 14:58:31.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','C_BPartner_Effective_ID',30,30,0,TO_TIMESTAMP('2025-09-30 14:58:31.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:33.782Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754285,0,542148,637341,TO_TIMESTAMP('2025-09-30 14:58:32.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:32.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> read.DropShip_BPartner_Effective_ID
-- Column: C_OLCand.DropShip_BPartner_Effective_ID
-- 2025-09-30T14:58:34.725Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754298,0,548442,553560,637342,'F',TO_TIMESTAMP('2025-09-30 14:58:33.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','DropShip_BPartner_Effective_ID',45,0,0,TO_TIMESTAMP('2025-09-30 14:58:33.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:35.858Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754299,0,542149,637342,TO_TIMESTAMP('2025-09-30 14:58:34.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:34.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> read.HandOver_Partner_Effective_ID
-- Column: C_OLCand.HandOver_Partner_Effective_ID
-- 2025-09-30T14:58:36.822Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754301,0,548442,553560,637343,'F',TO_TIMESTAMP('2025-09-30 14:58:35.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','HandOver_Partner_Effective_ID',65,0,0,TO_TIMESTAMP('2025-09-30 14:58:35.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:37.971Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754303,0,542150,637343,TO_TIMESTAMP('2025-09-30 14:58:37.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:37.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> read.Rechnungspartner
-- Column: C_OLCand.Bill_BPartner_ID
-- 2025-09-30T14:58:38.923Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754258,0,548442,553560,637344,'F',TO_TIMESTAMP('2025-09-30 14:58:38.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartners für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N','Rechnungspartner',80,0,0,TO_TIMESTAMP('2025-09-30 14:58:38.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:40.055Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754259,0,542151,637344,TO_TIMESTAMP('2025-09-30 14:58:39.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:39.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:40.855Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754257,0,542152,637344,TO_TIMESTAMP('2025-09-30 14:58:40.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2025-09-30 14:58:40.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10
-- UI Element Group: ship
-- 2025-09-30T14:58:41.487Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548484,553561,TO_TIMESTAMP('2025-09-30 14:58:40.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','ship',20,TO_TIMESTAMP('2025-09-30 14:58:40.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> ship.Lieferart
-- Column: C_OLCand.DeliveryRule
-- 2025-09-30T14:58:42.689Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754264,0,548442,553561,637345,'F',TO_TIMESTAMP('2025-09-30 14:58:41.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Lieferart',10,0,0,TO_TIMESTAMP('2025-09-30 14:58:41.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> ship.Lager dest.
-- Column: C_OLCand.M_Warehouse_Dest_ID
-- 2025-09-30T14:58:43.766Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754251,0,548442,553561,637346,'F',TO_TIMESTAMP('2025-09-30 14:58:42.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Lager dest.',20,0,0,TO_TIMESTAMP('2025-09-30 14:58:42.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> ship.Lager
-- Column: C_OLCand.M_Warehouse_ID
-- 2025-09-30T14:58:44.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754323,0,548442,553561,637347,'F',TO_TIMESTAMP('2025-09-30 14:58:44.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',30,0,0,TO_TIMESTAMP('2025-09-30 14:58:44.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub
-- UI Column: 20
-- 2025-09-30T14:58:45.628Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548485,546965,TO_TIMESTAMP('2025-09-30 14:58:45.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-30 14:58:45.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20
-- UI Element Group: write
-- 2025-09-30T14:58:46.390Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548485,553562,TO_TIMESTAMP('2025-09-30 14:58:45.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','write',10,TO_TIMESTAMP('2025-09-30 14:58:45.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> write.M_Product_Override_ID
-- Column: C_OLCand.M_Product_Override_ID
-- 2025-09-30T14:58:47.569Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754287,0,548442,553562,637348,'F',TO_TIMESTAMP('2025-09-30 14:58:46.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','M_Product_Override_ID',10,0,0,TO_TIMESTAMP('2025-09-30 14:58:46.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:48.755Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754291,0,542153,637348,TO_TIMESTAMP('2025-09-30 14:58:47.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:47.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> write.C_BPartner_Override_ID
-- Column: C_OLCand.C_BPartner_Override_ID
-- 2025-09-30T14:58:49.710Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754280,0,548442,553562,637349,'F',TO_TIMESTAMP('2025-09-30 14:58:48.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','C_BPartner_Override_ID',30,0,0,TO_TIMESTAMP('2025-09-30 14:58:48.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:50.875Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754281,0,542154,637349,TO_TIMESTAMP('2025-09-30 14:58:49.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:49.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> write.DropShip_BPartner_Override_ID
-- Column: C_OLCand.DropShip_BPartner_Override_ID
-- 2025-09-30T14:58:51.832Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754296,0,548442,553562,637350,'F',TO_TIMESTAMP('2025-09-30 14:58:51.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','DropShip_BPartner_Override_ID',45,0,0,TO_TIMESTAMP('2025-09-30 14:58:51.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:52.930Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754297,0,542155,637350,TO_TIMESTAMP('2025-09-30 14:58:52.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:52.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> write.HandOver_Partner_Override_ID
-- Column: C_OLCand.HandOver_Partner_Override_ID
-- 2025-09-30T14:58:53.892Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754300,0,548442,553562,637351,'F',TO_TIMESTAMP('2025-09-30 14:58:53.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','HandOver_Partner_Override_ID',65,0,0,TO_TIMESTAMP('2025-09-30 14:58:53.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:58:54.998Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754302,0,542156,637351,TO_TIMESTAMP('2025-09-30 14:58:54.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:58:54.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20
-- UI Element Group: org
-- 2025-09-30T14:58:55.625Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548485,553563,TO_TIMESTAMP('2025-09-30 14:58:55.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-09-30 14:58:55.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> org.Sektion
-- Column: C_OLCand.AD_Org_ID
-- 2025-09-30T14:58:56.793Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754243,0,548442,553563,637352,'F',TO_TIMESTAMP('2025-09-30 14:58:55.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Sektion',10,180,0,TO_TIMESTAMP('2025-09-30 14:58:55.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> org.Mandant
-- Column: C_OLCand.AD_Client_ID
-- 2025-09-30T14:58:57.947Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754239,0,548442,553563,637353,'F',TO_TIMESTAMP('2025-09-30 14:58:57.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2025-09-30 14:58:57.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate)
-- UI Section: advanced edit
-- 2025-09-30T14:58:58.700Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548442,546966,TO_TIMESTAMP('2025-09-30 14:58:58.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',30,TO_TIMESTAMP('2025-09-30 14:58:58.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2025-09-30T14:58:58.757Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546966 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-09-30T14:58:58.871Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546966
;

-- 2025-09-30T14:58:58.927Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546966, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540407
;

-- UI Section: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit
-- UI Column: 10
-- 2025-09-30T14:58:59.711Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548486,546966,TO_TIMESTAMP('2025-09-30 14:58:59.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-30 14:58:59.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2025-09-30T14:59:00.472Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548486,553564,TO_TIMESTAMP('2025-09-30 14:58:59.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2025-09-30 14:58:59.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.AD_InputDataSource_ID
-- Column: C_OLCand.AD_InputDataSource_ID
-- 2025-09-30T14:59:01.665Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754248,0,548442,553564,637354,'F',TO_TIMESTAMP('2025-09-30 14:59:00.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','AD_InputDataSource_ID',10,0,0,TO_TIMESTAMP('2025-09-30 14:59:00.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.AD_DataDestination_ID
-- Column: C_OLCand.AD_DataDestination_ID
-- 2025-09-30T14:59:02.733Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754266,0,548442,553564,637355,'F',TO_TIMESTAMP('2025-09-30 14:59:01.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','AD_DataDestination_ID',20,0,0,TO_TIMESTAMP('2025-09-30 14:59:01.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Erfasst durch
-- Column: C_OLCand.AD_User_EnteredBy_ID
-- 2025-09-30T14:59:03.795Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754233,0,548442,553564,637356,'F',TO_TIMESTAMP('2025-09-30 14:59:02.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Erfasst durch',30,0,0,TO_TIMESTAMP('2025-09-30 14:59:02.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Datensatz-ID
-- Column: C_OLCand.Record_ID
-- 2025-09-30T14:59:04.858Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754263,0,548442,553564,637357,'F',TO_TIMESTAMP('2025-09-30 14:59:04.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','Y','N','Y','N','N','N','Datensatz-ID',40,0,0,TO_TIMESTAMP('2025-09-30 14:59:04.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.ExternalHeaderId
-- Column: C_OLCand.ExternalHeaderId
-- 2025-09-30T14:59:05.933Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754312,0,548442,553564,637358,'F',TO_TIMESTAMP('2025-09-30 14:59:05.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'ExternalHeaderId',50,0,0,TO_TIMESTAMP('2025-09-30 14:59:05.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.ExternalLineId
-- Column: C_OLCand.ExternalLineId
-- 2025-09-30T14:59:06.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754308,0,548442,553564,637359,'F',TO_TIMESTAMP('2025-09-30 14:59:06.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'ExternalLineId',60,0,0,TO_TIMESTAMP('2025-09-30 14:59:06.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.M_Product_ID
-- Column: C_OLCand.M_Product_ID
-- 2025-09-30T14:59:08.082Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754244,0,548442,553564,637360,'F',TO_TIMESTAMP('2025-09-30 14:59:07.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','Y','N','N','N','M_Product_ID',70,0,0,TO_TIMESTAMP('2025-09-30 14:59:07.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:59:09.209Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754276,0,542157,637360,TO_TIMESTAMP('2025-09-30 14:59:08.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:59:08.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.IsManualQtyItemCapacit
-- Column: C_OLCand.IsManualQtyItemCapacity
-- 2025-09-30T14:59:10.180Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754304,0,548442,553564,637361,'F',TO_TIMESTAMP('2025-09-30 14:59:09.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn "nein", dann wird die interne Verpackungskapazität zugrunde gelegt','Y','N','N','Y','N','N','N',0,'IsManualQtyItemCapacit',128,0,0,TO_TIMESTAMP('2025-09-30 14:59:09.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.QtyItemCapacity
-- Column: C_OLCand.QtyItemCapacity
-- 2025-09-30T14:59:11.251Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754277,0,548442,553564,637362,'F',TO_TIMESTAMP('2025-09-30 14:59:10.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','QtyItemCapacity',130,0,0,TO_TIMESTAMP('2025-09-30 14:59:10.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.QtyItemCapacityInternal
-- Column: C_OLCand.QtyItemCapacityInternal
-- 2025-09-30T14:59:12.340Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754344,0,548442,553564,637363,'F',TO_TIMESTAMP('2025-09-30 14:59:11.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'In den metasfresh Stammdaten hinterlegte Verpackungskapazität','Y','N','N','Y','N','N','N',0,'QtyItemCapacityInternal',132,0,0,TO_TIMESTAMP('2025-09-30 14:59:11.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Preiseinheit int.
-- Column: C_OLCand.Price_UOM_Internal_ID
-- 2025-09-30T14:59:13.443Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754290,0,548442,553564,637364,'F',TO_TIMESTAMP('2025-09-30 14:59:12.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Interne Preiseinheit laut Stammdaten','Y','Y','N','Y','N','N','N','Preiseinheit int.',160,0,0,TO_TIMESTAMP('2025-09-30 14:59:12.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Auftragsdatum
-- Column: C_OLCand.DateOrdered
-- 2025-09-30T14:59:14.510Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754313,0,548442,553564,637365,'F',TO_TIMESTAMP('2025-09-30 14:59:13.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','Y','N','Y','N','N','N','Auftragsdatum',165,0,0,TO_TIMESTAMP('2025-09-30 14:59:13.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zugesagter Termin
-- Column: C_OLCand.DatePromised
-- 2025-09-30T14:59:15.836Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754278,0,548442,553564,637366,'F',TO_TIMESTAMP('2025-09-30 14:59:14.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','Y','N','Y','N','N','N','Zugesagter Termin',170,0,0,TO_TIMESTAMP('2025-09-30 14:59:14.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zugesagter Termin abw.
-- Column: C_OLCand.DatePromised_Override
-- 2025-09-30T14:59:16.917Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754282,0,548442,553564,637367,'F',TO_TIMESTAMP('2025-09-30 14:59:16.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','Y','N','Y','N','N','N','Zugesagter Termin abw.',180,0,0,TO_TIMESTAMP('2025-09-30 14:59:16.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zugesagter Termin eff.
-- Column: C_OLCand.DatePromised_Effective
-- 2025-09-30T14:59:17.992Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754283,0,548442,553564,637368,'F',TO_TIMESTAMP('2025-09-30 14:59:17.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','Y','N','Y','N','N','N','Zugesagter Termin eff.',190,0,0,TO_TIMESTAMP('2025-09-30 14:59:17.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Beschreibung
-- Column: C_OLCand.Description
-- 2025-09-30T14:59:19.059Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754232,0,548442,553564,637369,'F',TO_TIMESTAMP('2025-09-30 14:59:18.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Beschreibung',200,0,0,TO_TIMESTAMP('2025-09-30 14:59:18.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Produktbeschreibung
-- Column: C_OLCand.ProductDescription
-- 2025-09-30T14:59:20.148Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754295,0,548442,553564,637370,'F',TO_TIMESTAMP('2025-09-30 14:59:19.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produktbeschreibung','Y','Y','N','Y','N','N','N','Produktbeschreibung',210,0,0,TO_TIMESTAMP('2025-09-30 14:59:19.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.C_BPartner_ID
-- Column: C_OLCand.C_BPartner_ID
-- 2025-09-30T14:59:21.252Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754236,0,548442,553564,637371,'F',TO_TIMESTAMP('2025-09-30 14:59:20.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','Y','N','N','N','C_BPartner_ID',220,0,0,TO_TIMESTAMP('2025-09-30 14:59:20.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:59:22.383Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754245,0,542158,637371,TO_TIMESTAMP('2025-09-30 14:59:21.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:59:21.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:59:23.223Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754246,0,542159,637371,TO_TIMESTAMP('2025-09-30 14:59:22.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2025-09-30 14:59:22.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Übergabe an
-- Column: C_OLCand.HandOver_Partner_ID
-- 2025-09-30T14:59:24.192Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754273,0,548442,553564,637372,'F',TO_TIMESTAMP('2025-09-30 14:59:23.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Übergabe an',300,0,0,TO_TIMESTAMP('2025-09-30 14:59:23.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:59:25.324Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754272,0,542160,637372,TO_TIMESTAMP('2025-09-30 14:59:24.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:59:24.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Merkmale
-- Column: C_OLCand.M_AttributeSetInstance_ID
-- 2025-09-30T14:59:26.277Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754231,0,548442,553564,637373,'F',TO_TIMESTAMP('2025-09-30 14:59:25.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','Y','N','Y','N','N','N','Merkmale',370,0,0,TO_TIMESTAMP('2025-09-30 14:59:25.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Preis imp.
-- Column: C_OLCand.PriceEntered
-- 2025-09-30T14:59:27.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754255,0,548442,553564,637374,'F',TO_TIMESTAMP('2025-09-30 14:59:26.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Importierter Preis','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet','Y','Y','N','Y','N','N','N','Preis imp.',380,0,0,TO_TIMESTAMP('2025-09-30 14:59:26.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Preis int.
-- Column: C_OLCand.PriceInternal
-- 2025-09-30T14:59:28.423Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754289,0,548442,553564,637375,'F',TO_TIMESTAMP('2025-09-30 14:59:27.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Interner Preis laut Stammdaten','Y','Y','N','Y','N','N','N','Preis int.',390,0,0,TO_TIMESTAMP('2025-09-30 14:59:27.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Rabatt %
-- Column: C_OLCand.Discount
-- 2025-09-30T14:59:29.552Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754256,0,548442,553564,637376,'F',TO_TIMESTAMP('2025-09-30 14:59:28.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Abschlag in Prozent','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','Y','N','Y','N','N','N','Rabatt %',430,0,0,TO_TIMESTAMP('2025-09-30 14:59:28.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Fehlermeldung
-- Column: C_OLCand.ErrorMsg
-- 2025-09-30T14:59:30.617Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754252,0,548442,553564,637377,'F',TO_TIMESTAMP('2025-09-30 14:59:29.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Fehlermeldung',460,0,0,TO_TIMESTAMP('2025-09-30 14:59:29.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zeile Nr.
-- Column: C_OLCand.Line
-- 2025-09-30T14:59:31.688Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754271,0,548442,553564,637378,'F',TO_TIMESTAMP('2025-09-30 14:59:30.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','Y','N','Y','N','N','N','Zeile Nr.',480,0,0,TO_TIMESTAMP('2025-09-30 14:59:30.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Lieferempfänger
-- Column: C_OLCand.DropShip_BPartner_ID
-- 2025-09-30T14:59:32.766Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754275,0,548442,553564,637379,'F',TO_TIMESTAMP('2025-09-30 14:59:31.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner to ship to','If empty the business partner will be shipped to.','Y','Y','N','Y','N','N','N','Lieferempfänger',510,0,0,TO_TIMESTAMP('2025-09-30 14:59:31.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T14:59:33.905Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754274,0,542161,637379,TO_TIMESTAMP('2025-09-30 14:59:33.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-09-30 14:59:33.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Lieferung
-- Column: C_OLCand.DeliveryViaRule
-- 2025-09-30T14:59:34.868Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754265,0,548442,553564,637380,'F',TO_TIMESTAMP('2025-09-30 14:59:34.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Lieferung',520,0,0,TO_TIMESTAMP('2025-09-30 14:59:34.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Preissystem
-- Column: C_OLCand.M_PricingSystem_ID
-- 2025-09-30T14:59:35.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754267,0,548442,553564,637381,'F',TO_TIMESTAMP('2025-09-30 14:59:35.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Preissystem',530,0,0,TO_TIMESTAMP('2025-09-30 14:59:35.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Vorbelegtes Lieferdatum
-- Column: C_OLCand.PresetDateShipped
-- 2025-09-30T14:59:37Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754314,0,548442,553564,637382,'F',TO_TIMESTAMP('2025-09-30 14:59:36.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Vorbelegtes Lieferdatum',535,0,0,TO_TIMESTAMP('2025-09-30 14:59:36.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Rechnungsdatum
-- Column: C_OLCand.PresetDateInvoiced
-- 2025-09-30T14:59:38.083Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754309,0,548442,553564,637383,'F',TO_TIMESTAMP('2025-09-30 14:59:37.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Rechnungsdatum',540,0,0,TO_TIMESTAMP('2025-09-30 14:59:37.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Rechnungs-Belegart
-- Column: C_OLCand.C_DocTypeInvoice_ID
-- 2025-09-30T14:59:39.162Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754310,0,548442,553564,637384,'F',TO_TIMESTAMP('2025-09-30 14:59:38.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Rechnungs-Belegart',550,0,0,TO_TIMESTAMP('2025-09-30 14:59:38.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Steuerkategorie
-- Column: C_OLCand.C_TaxCategory_ID
-- 2025-09-30T14:59:40.243Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754311,0,548442,553564,637385,'F',TO_TIMESTAMP('2025-09-30 14:59:39.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Steuerkategorie',560,0,0,TO_TIMESTAMP('2025-09-30 14:59:39.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Auftrags-Belegart
-- Column: C_OLCand.C_DocTypeOrder_ID
-- 2025-09-30T14:59:41.331Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754319,0,548442,553564,637386,'F',TO_TIMESTAMP('2025-09-30 14:59:40.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document type used for the orders generated from this order candidate','The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','Y','N','Y','N','N','N',0,'Auftrags-Belegart',565,0,0,TO_TIMESTAMP('2025-09-30 14:59:40.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zahlungsweise
-- Column: C_OLCand.PaymentRule
-- 2025-09-30T14:59:42.423Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754318,0,548442,553564,637387,'F',TO_TIMESTAMP('2025-09-30 14:59:41.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','Y','N','Y','N','N','N',0,'Zahlungsweise',580,0,0,TO_TIMESTAMP('2025-09-30 14:59:41.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zahlungsbedingung
-- Column: C_OLCand.C_PaymentTerm_ID
-- 2025-09-30T14:59:43.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754322,0,548442,553564,637388,'F',TO_TIMESTAMP('2025-09-30 14:59:42.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Bedingungen für die Bezahlung dieses Vorgangs','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','Y','N','Y','N','N','N',0,'Zahlungsbedingung',585,0,0,TO_TIMESTAMP('2025-09-30 14:59:42.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zugeordneter Vertriebspartner
-- Column: C_OLCand.C_BPartner_SalesRep_ID
-- 2025-09-30T14:59:44.600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754317,0,548442,553564,637389,'F',TO_TIMESTAMP('2025-09-30 14:59:43.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Zugeordneter Vertriebspartner',590,0,0,TO_TIMESTAMP('2025-09-30 14:59:43.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Vertriebpartner int.
-- Column: C_OLCand.C_BPartner_SalesRep_Internal_ID
-- 2025-09-30T14:59:45.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754337,0,548442,553564,637390,'F',TO_TIMESTAMP('2025-09-30 14:59:44.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Vertriebpartner int.',595,0,0,TO_TIMESTAMP('2025-09-30 14:59:44.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Sales rep from
-- Column: C_OLCand.ApplySalesRepFrom
-- 2025-09-30T14:59:46.793Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754336,0,548442,553564,637391,'F',TO_TIMESTAMP('2025-09-30 14:59:45.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Sales rep from',596,0,0,TO_TIMESTAMP('2025-09-30 14:59:45.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Lieferweg
-- Column: C_OLCand.M_Shipper_ID
-- 2025-09-30T14:59:47.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754316,0,548442,553564,637392,'F',TO_TIMESTAMP('2025-09-30 14:59:47.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','Y','N','Y','N','N','N',0,'Lieferweg',600,0,0,TO_TIMESTAMP('2025-09-30 14:59:47.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zugesagter Termin eff.
-- Column: C_OLCand.DatePromised_Effective
-- 2025-09-30T14:59:48.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754283,0,548442,553564,637393,'F',TO_TIMESTAMP('2025-09-30 14:59:48.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','Y','N','Y','Y','N','N',0,'Zugesagter Termin eff.',640,110,0,TO_TIMESTAMP('2025-09-30 14:59:48.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.CompensationGroupKey
-- Column: C_OLCand.CompensationGroupKey
-- 2025-09-30T14:59:50.015Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754325,0,548442,553564,637394,'F',TO_TIMESTAMP('2025-09-30 14:59:49.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'CompensationGroupKey',650,0,0,TO_TIMESTAMP('2025-09-30 14:59:49.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.IsGroupCompensationLine
-- Column: C_OLCand.IsGroupCompensationLine
-- 2025-09-30T14:59:51.083Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754324,0,548442,553564,637395,'F',TO_TIMESTAMP('2025-09-30 14:59:50.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'IsGroupCompensationLine',660,0,0,TO_TIMESTAMP('2025-09-30 14:59:50.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Name Geschäftspartner
-- Column: C_OLCand.BPartnerName
-- 2025-09-30T14:59:52.147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754338,0,548442,553564,637396,'F',TO_TIMESTAMP('2025-09-30 14:59:51.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Name Geschäftspartner',670,0,0,TO_TIMESTAMP('2025-09-30 14:59:51.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.eMail
-- Column: C_OLCand.EMail
-- 2025-09-30T14:59:53.201Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754339,0,548442,553564,637397,'F',TO_TIMESTAMP('2025-09-30 14:59:52.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','Y','N','N','N',0,'eMail',680,0,0,TO_TIMESTAMP('2025-09-30 14:59:52.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Telefon
-- Column: C_OLCand.Phone
-- 2025-09-30T14:59:54.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754340,0,548442,553564,637398,'F',TO_TIMESTAMP('2025-09-30 14:59:53.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beschreibt eine Telefon Nummer','Y','Y','N','Y','N','N','N',0,'Telefon',690,0,0,TO_TIMESTAMP('2025-09-30 14:59:53.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- 2025-09-30T14:59:55.331Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754349,0,548442,553564,637399,'F',TO_TIMESTAMP('2025-09-30 14:59:54.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','Y','N','Y','N','N','N','Rechnungsstellung',700,0,0,TO_TIMESTAMP('2025-09-30 14:59:54.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10
-- UI Element Group: qty shipped
-- 2025-09-30T14:59:56.083Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548486,553565,TO_TIMESTAMP('2025-09-30 14:59:55.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','qty shipped',20,TO_TIMESTAMP('2025-09-30 14:59:55.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Qty Shipped
-- Column: C_OLCand.QtyShipped
-- 2025-09-30T14:59:57.248Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754343,0,548442,553565,637400,'F',TO_TIMESTAMP('2025-09-30 14:59:56.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Qty Shipped',10,0,0,TO_TIMESTAMP('2025-09-30 14:59:56.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- 2025-09-30T14:59:58.338Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754350,0,548442,553565,637401,'F',TO_TIMESTAMP('2025-09-30 14:59:57.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Shipped Weight',20,0,0,TO_TIMESTAMP('2025-09-30 14:59:57.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- 2025-09-30T14:59:59.403Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754351,0,548442,553565,637402,'F',TO_TIMESTAMP('2025-09-30 14:59:58.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Shipped Weight UOM',30,0,0,TO_TIMESTAMP('2025-09-30 14:59:58.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate)
-- UI Section: main
-- 2025-09-30T15:00:00.276Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548443,546967,TO_TIMESTAMP('2025-09-30 14:59:59.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-30 14:59:59.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-30T15:00:00.334Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546967 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-09-30T15:00:00.450Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546967
;

-- 2025-09-30T15:00:00.508Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546967, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540406
;

-- UI Section: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main
-- UI Column: 10
-- 2025-09-30T15:00:01.202Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548487,546967,TO_TIMESTAMP('2025-09-30 15:00:00.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-30 15:00:00.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10
-- UI Element Group: default
-- 2025-09-30T15:00:01.953Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548487,553566,TO_TIMESTAMP('2025-09-30 15:00:01.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-09-30 15:00:01.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10 -> default.Auftragsposition
-- Column: C_Order_Line_Alloc.C_OrderLine_ID
-- 2025-09-30T15:00:03.061Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754358,0,548443,553566,637403,'F',TO_TIMESTAMP('2025-09-30 15:00:02.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','Y','Y','N','N','Auftragsposition',10,10,0,TO_TIMESTAMP('2025-09-30 15:00:02.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10 -> default.Bestellte Menge
-- Column: C_Order_Line_Alloc.QtyOrdered
-- 2025-09-30T15:00:04.049Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754360,0,548443,553566,637404,'F',TO_TIMESTAMP('2025-09-30 15:00:03.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellte Menge','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','Y','N','N','Bestellte Menge',20,20,0,TO_TIMESTAMP('2025-09-30 15:00:03.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10 -> default.Belegstatus
-- Column: C_Order_Line_Alloc.DocStatus
-- 2025-09-30T15:00:05.061Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754359,0,548443,553566,637405,'F',TO_TIMESTAMP('2025-09-30 15:00:04.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','Y','N','N','Belegstatus',30,30,0,TO_TIMESTAMP('2025-09-30 15:00:04.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10 -> default.Auftragskand. Verarb.
-- Column: C_Order_Line_Alloc.C_OLCandProcessor_ID
-- 2025-09-30T15:00:06.053Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754357,0,548443,553566,637406,'F',TO_TIMESTAMP('2025-09-30 15:00:05.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Auftragskand. Verarb.',40,40,0,TO_TIMESTAMP('2025-09-30 15:00:05.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10 -> default.Sektion
-- Column: C_Order_Line_Alloc.AD_Org_ID
-- 2025-09-30T15:00:07.060Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754364,0,548443,553566,637407,'F',TO_TIMESTAMP('2025-09-30 15:00:06.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Sektion',50,50,0,TO_TIMESTAMP('2025-09-30 15:00:06.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Zuordnung - Auftragszeile(548443,de.metas.ordercandidate) -> main -> 10 -> default.Mandant
-- Column: C_Order_Line_Alloc.AD_Client_ID
-- 2025-09-30T15:00:08.054Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754363,0,548443,553566,637408,'F',TO_TIMESTAMP('2025-09-30 15:00:07.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',60,0,0,TO_TIMESTAMP('2025-09-30 15:00:07.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Window: Auftragsdisposition, InternalName=null
-- 2025-09-30T15:00:35.308Z
UPDATE AD_Window SET Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2025-09-30 15:00:35.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541952
;

-- 2025-09-30T15:01:56.979Z
UPDATE AD_Window_Trl SET Name='Auftragsdisposition',Updated=TO_TIMESTAMP('2025-09-30 15:01:56.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Window_ID=541952
;

-- 2025-09-30T15:01:57.038Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Name: Auftragsdisposition
-- Action Type: W
-- Window: Auftragsdisposition(541952,de.metas.ordercandidate)
-- 2025-09-30T15:02:30.407Z
UPDATE AD_Menu SET AD_Element_ID=574745, AD_Window_ID=541952, Description=NULL, Name='Auftragsdisposition', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2025-09-30 15:02:30.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=1000097
;

-- 2025-09-30T15:02:30.465Z
UPDATE AD_Menu_Trl trl SET Name='Auftragsdisposition' WHERE AD_Menu_ID=1000097 AND AD_Language='de_DE'
;

-- 2025-09-30T15:02:30.525Z
/* DDL */  select update_menu_translation_from_ad_element(574745)
;

-- Name: Auftragsdisposition (Legacy-EDI-Import)
-- Action Type: W
-- Window: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate)
-- 2025-09-30T15:03:09.213Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584049,542255,0,540095,TO_TIMESTAMP('2025-09-30 15:03:08.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ordercandidate','C_OLCand','Y','N','N','N','N','Auftragsdisposition (Legacy-EDI-Import)',TO_TIMESTAMP('2025-09-30 15:03:08.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-30T15:03:09.272Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542255 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-30T15:03:09.332Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542255, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542255)
;

-- 2025-09-30T15:03:09.396Z
/* DDL */  select update_menu_translation_from_ad_element(584049)
;

-- Reordering children of `Sales Orders`
-- Node name: `Web POS`
-- 2025-09-30T15:03:13.619Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=52001 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition`
-- 2025-09-30T15:03:13.677Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540241 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition - Fehler`
-- 2025-09-30T15:03:13.734Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540311 AND AD_Tree_ID=10
;

-- Node name: `Auftragskand. Prozessor`
-- 2025-09-30T15:03:13.791Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540205 AND AD_Tree_ID=10
;

-- Node name: `Market Place`
-- 2025-09-30T15:03:13.848Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=460 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2025-09-30T15:03:13.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=301 AND AD_Tree_ID=10
;

-- Node name: `Sales Order`
-- 2025-09-30T15:03:13.963Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=129 AND AD_Tree_ID=10
;

-- Node name: `Order Detail`
-- 2025-09-30T15:03:14.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=543 AND AD_Tree_ID=10
;

-- Node name: `Open Orders`
-- 2025-09-30T15:03:14.076Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=195 AND AD_Tree_ID=10
;

-- Node name: `Order Transactions`
-- 2025-09-30T15:03:14.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53223 AND AD_Tree_ID=10
;

-- Node name: `Generate PO from Sales Order`
-- 2025-09-30T15:03:14.189Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=335 AND AD_Tree_ID=10
;

-- Node name: `Reopen Order`
-- 2025-09-30T15:03:14.247Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=436 AND AD_Tree_ID=10
;

-- Node name: `Order Batch Process`
-- 2025-09-30T15:03:14.302Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=507 AND AD_Tree_ID=10
;

-- Node name: `POS Key Layout`
-- 2025-09-30T15:03:14.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=492 AND AD_Tree_ID=10
;

-- Node name: `POS Key Generate`
-- 2025-09-30T15:03:14.417Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53269 AND AD_Tree_ID=10
;

-- Node name: `POS Terminal`
-- 2025-09-30T15:03:14.475Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=491 AND AD_Tree_ID=10
;

-- Node name: `Bestellkontrolle zum Kunden Drucken`
-- 2025-09-30T15:03:14.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540583 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (Legacy-EDI-Import)`
-- 2025-09-30T15:03:14.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=457, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542255 AND AD_Tree_ID=10
;

-- Reordering children of `Sales`
-- Node name: `CreditPass configuration`
-- 2025-09-30T15:03:18.928Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order`
-- 2025-09-30T15:03:18.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request`
-- 2025-09-30T15:03:19.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition`
-- 2025-09-30T15:03:19.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (Legacy-EDI-Import)`
-- 2025-09-30T15:03:19.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542255 AND AD_Tree_ID=10
;

-- Node name: `Order Control`
-- 2025-09-30T15:03:19.219Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2025-09-30T15:03:19.277Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type`
-- 2025-09-30T15:03:19.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-09-30T15:03:19.392Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-09-30T15:03:19.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-09-30T15:03:19.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results`
-- 2025-09-30T15:03:19.565Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2025-09-30T15:03:19.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm`
-- 2025-09-30T15:03:19.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Available for sales`
-- 2025-09-30T15:03:19.741Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541962 AND AD_Tree_ID=10
;

-- Node name: `Point of Sale (POS)`
-- 2025-09-30T15:03:19.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542171 AND AD_Tree_ID=10
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.EXP_ReplicationTrx_ID
-- Column: C_OLCand.EXP_ReplicationTrx_ID
-- 2025-09-30T15:07:49.968Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-09-30 15:07:49.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637323
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Referenz
-- Column: C_OLCand.POReference
-- 2025-09-30T15:07:50.327Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-30 15:07:50.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637333
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 10 -> read.Produkt eff.
-- Column: C_OLCand.M_Product_Effective_ID
-- 2025-09-30T15:07:50.679Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-30 15:07:50.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637340
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.QtyEntered
-- Column: C_OLCand.QtyEntered
-- 2025-09-30T15:07:51.026Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-30 15:07:51.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637324
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> price.Preis eff.
-- Column: C_OLCand.PriceActual
-- 2025-09-30T15:07:51.372Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-30 15:07:51.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637327
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Währung
-- Column: C_OLCand.C_Currency_ID
-- 2025-09-30T15:07:51.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-30 15:07:51.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637334
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> price.PriceDifference
-- Column: C_OLCand.PriceDifference
-- 2025-09-30T15:07:52.071Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-30 15:07:52.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637329
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Zugesagter Termin eff.
-- Column: C_OLCand.DatePromised_Effective
-- 2025-09-30T15:07:52.433Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-09-30 15:07:52.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637393
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Fehler
-- Column: C_OLCand.IsError
-- 2025-09-30T15:07:52.781Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-09-30 15:07:52.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637332
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Gruppierungsfehler
-- Column: C_OLCand.IsGroupingError
-- 2025-09-30T15:07:53.127Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-09-30 15:07:53.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637335
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: C_OLCand.Processed
-- 2025-09-30T15:07:53.467Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-09-30 15:07:53.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637331
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2025-09-30T15:07:53.812Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-09-30 15:07:53.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637337
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication trx error
-- Column: C_OLCand.IsReplicationTrxError
-- 2025-09-30T15:07:54.157Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-09-30 15:07:54.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637338
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> sub -> 20 -> org.Sektion
-- Column: C_OLCand.AD_Org_ID
-- 2025-09-30T15:07:54.506Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-09-30 15:07:54.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637352
;

-- UI Column: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10
-- UI Element Group: External
-- 2025-09-30T15:09:32.948Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548482,553567,TO_TIMESTAMP('2025-09-30 15:09:32.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','External',30,TO_TIMESTAMP('2025-09-30 15:09:32.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> External.ExternalHeaderId
-- Column: C_OLCand.ExternalHeaderId
-- 2025-09-30T15:10:17.171Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553567, SeqNo=10,Updated=TO_TIMESTAMP('2025-09-30 15:10:17.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637358
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> External.ExternalLineId
-- Column: C_OLCand.ExternalLineId
-- 2025-09-30T15:10:41.630Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=553567, SeqNo=20,Updated=TO_TIMESTAMP('2025-09-30 15:10:41.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637359
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> External.ExternalLineId
-- Column: C_OLCand.ExternalLineId
-- 2025-09-30T15:11:02.765Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2025-09-30 15:11:02.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637359
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> External.ExternalHeaderId
-- Column: C_OLCand.ExternalHeaderId
-- 2025-09-30T15:11:07.485Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2025-09-30 15:11:07.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637358
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2025-09-30T15:12:16.955Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-09-30 15:12:16.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637337
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication trx error
-- Column: C_OLCand.IsReplicationTrxError
-- 2025-09-30T15:12:17.298Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-09-30 15:12:17.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637338
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.ExternalHeaderId
-- Column: C_OLCand.ExternalHeaderId
-- 2025-09-30T15:12:17.646Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-09-30 15:12:17.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637321
;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> main -> 10 -> default.ExternalLineId
-- Column: C_OLCand.ExternalLineId
-- 2025-09-30T15:12:17.994Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-09-30 15:12:17.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637322
;

