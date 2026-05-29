-- Run mode: SWING_CLIENT

-- UI Element: Lieferantenrücklieferung(53098,D) -> Positionen(53277,D) -> main -> 10 -> default.Return_Origin_InOutLine_ID
-- Column: M_InOutLine.Return_Origin_InOutLine_ID
-- 2026-03-10T11:17:41.236Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 558145, 0, 53277, 540105, 648540, 'F', TO_TIMESTAMP('2026-03-10 11:17:41.080000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Original receipt line containing the products that are returned.', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Return_Origin_InOutLine_ID', 95, 0, 0,
        TO_TIMESTAMP('2026-03-10 11:17:41.080000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- UI Element: Lieferantenrücklieferung(53098,D) -> Lieferantenrücklieferung(53276,D) -> advanced edit -> 10 -> advanced edit.Ursprüngliche Lieferung
-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-03-10T11:19:51.418Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774826, 0, 53276, 540104, 648541, 'F', TO_TIMESTAMP('2026-03-10 11:19:51.298000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 'Y', 'N', 'Y', 'N', 'N', 'N', 0, 'Ursprüngliche Lieferung', 360, 0, 0, TO_TIMESTAMP('2026-03-10 11:19:51.298000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- Name: M_InOut PO
-- 2026-03-10T11:22:05.373Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542075, TO_TIMESTAMP('2026-03-10 11:22:05.217000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'N', 'M_InOut PO', TO_TIMESTAMP('2026-03-10 11:22:05.217000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'T')
;

-- 2026-03-10T11:22:05.376Z
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 542075
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Reference: M_InOut PO
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2026-03-10T11:22:46.433Z
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 3521, 0, 542075, 319, 184, TO_TIMESTAMP('2026-03-10 11:22:46.423000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2026-03-10 11:22:46.423000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'IsSOTrx=''N''')
;

-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-03-10T11:23:14.760Z
UPDATE AD_Column
SET AD_Reference_Value_ID=542075, IsExcludeFromZoomTargets='Y', Updated=TO_TIMESTAMP('2026-03-10 11:23:14.760000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 591915
;

-- 2026-03-10T11:27:58.427Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584649, 0, TO_TIMESTAMP('2026-03-10 11:27:58.307000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'Ursprüngliche Wareneingänge', 'Ursprüngliche Wareneingänge', TO_TIMESTAMP('2026-03-10 11:27:58.307000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-10T11:27:58.431Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584649
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: null
-- 2026-03-10T11:28:15.622Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Original Receipt', PrintName='Original Receipt Wareneingänge', Updated=TO_TIMESTAMP('2026-03-10 11:28:15.622000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 584649
  AND AD_Language = 'en_US'
;

-- 2026-03-10T11:28:15.623Z
UPDATE AD_Element base
SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID = base.AD_Element_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage()
;

-- 2026-03-10T11:28:15.896Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584649, 'en_US')
;

-- Field: Lieferantenrücklieferung(53098,D) -> Lieferantenrücklieferung(53276,D) -> Ursprüngliche Wareneingänge
-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-03-10T11:28:51.263Z
UPDATE AD_Field
SET AD_Name_ID=584649, Description=NULL, Help=NULL, Name='Ursprüngliche Wareneingänge', Updated=TO_TIMESTAMP('2026-03-10 11:28:51.263000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Field_ID = 774826
;

-- 2026-03-10T11:28:51.264Z
UPDATE AD_Field_Trl trl
SET Name='Ursprüngliche Wareneingänge'
WHERE AD_Field_ID = 774826
  AND AD_Language = 'de_DE'
;

-- 2026-03-10T11:28:51.266Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(584649)
;

-- 2026-03-10T11:28:51.277Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774826
;

-- 2026-03-10T11:28:51.280Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774826)
;

-- Name: Shipment -> Customer Return
-- Source Reference: M_InOut SO
-- Target Reference: Customer Return target for Shipment
-- 2026-03-10T11:51:35.788Z
UPDATE AD_RelationType
SET AD_Reference_Source_ID=540299, Updated=TO_TIMESTAMP('2026-03-10 11:51:35.788000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_RelationType_ID = 540489
;

-- Name: Vendor Return target for Shipment
-- 2026-03-10T11:52:13.965Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542076, TO_TIMESTAMP('2026-03-10 11:52:13.840000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'N', 'Vendor Return target for Shipment', TO_TIMESTAMP('2026-03-10 11:52:13.840000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'T')
;

-- 2026-03-10T11:52:13.969Z
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 542076
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Reference: Vendor Return target for Shipment
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2026-03-10T11:53:33.458Z
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 3521, 0, 542076, 319, 53098, TO_TIMESTAMP('2026-03-10 11:53:33.453000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2026-03-10 11:53:33.453000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'M_InOut.Return_Origin_InOut_ID = @M_InOut_ID/-1@')
;

-- Reference: Customer Return target for Shipment
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2026-03-10T11:53:49.628Z
UPDATE AD_Ref_Table
SET AD_Window_ID=53097, Updated=TO_TIMESTAMP('2026-03-10 11:53:49.628000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Reference_ID = 542049
;

-- Name: Shipment -> Vendor Return
-- Source Reference: M_InOut PO
-- Target Reference: Vendor Return target for Shipment
-- 2026-03-10T11:55:58.113Z
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 542075, 542076, 540494, TO_TIMESTAMP('2026-03-10 11:55:57.988000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'de.metas.swat', 'Y', 'N', 'Shipment -> Vendor Return', TO_TIMESTAMP('2026-03-10 11:55:57.988000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;


-- From `@IsPackagingMaterial@=N & @IsManualPackingMaterial@=N & @IsManual@ = N`
-- Without this, it will not be possible to create partial vendor returns
-- Column: M_InOutLine.QtyEntered
-- 2026-03-10T14:22:56.036Z
UPDATE AD_Column
SET ReadOnlyLogic='', Updated=TO_TIMESTAMP('2026-03-10 14:22:56.036000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 12868
;

-- UI Element: Lieferantenrücklieferung(53098,D) -> Lieferantenrücklieferung(53276,D) -> advanced edit -> 10 -> advanced edit.Ursprüngliche Lieferung
-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-03-10T14:44:52.419Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 648541
;

-- UI Element: Lieferantenrücklieferung(53098,D) -> Lieferantenrücklieferung(53276,D) -> main -> 10 -> default.Ursprüngliche Wareneingänge
-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-03-10T14:45:35.414Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774826, 0, 53276, 540744, 648543, 'F', TO_TIMESTAMP('2026-03-10 14:45:35.175000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Ursprüngliche Wareneingänge', 30, 0, 0, TO_TIMESTAMP('2026-03-10 14:45:35.175000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- Tab: Lieferantenrücklieferung(53098,D) -> Handling Unit Assignment
-- Table: M_HU_Assignment
-- 2026-03-10T14:48:29.762Z
UPDATE AD_Tab
SET TabLevel=1, Updated=TO_TIMESTAMP('2026-03-10 14:48:29.761000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Tab_ID = 540794
;

