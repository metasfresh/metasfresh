-- Navigation from "Kostenüberwachung Fertigung" (AD_Window 542175 / AD_Tab 549352) to the full
-- manufacturing order in "Produktionsauftrag" (AD_Window 53009).
--
-- Both windows sit on PP_Order, so clicking a monitor row only re-opens it in the monitor's own
-- read-only window. A lookup field is what carries a zoom, so this adds a virtual FK column whose
-- value is the order's own id, rendered as the document number and zooming to window 53009.
--
-- Why a DEDICATED AD_Reference (Table) instead of plain Search-by-column-name: the zoom target of a
-- lookup comes from AD_Ref_Table.AD_Window_ID when the column carries an AD_Reference_Value_ID
-- (MLookupFactory.getLookup_Table -> ADRefTable.zoomAD_Window_ID_Override -> MLookupFactory ~L644,
-- which then wins over every other rule; DocumentZoomIntoService.getWindowId returns it before any
-- fallback runs). Naming 53009 there makes the target explicit data rather than a derived default.
-- The window the user is currently in is never an input to that resolution, so this cannot bounce
-- back into 542175. Same shape as S_Issue.Internal_Effort_S_Issue_ID / AD_Reference 541143, and the
-- same use of AD_Ref_Table.AD_Window_ID as 5751640_sys_gh20561_PLV_Zoom_Product_Window.sql.
--
-- Nothing may leak into window 53009 (or any other PP_Order window): AD_Column attributes are shared
-- by every window showing PP_Order, so column 593529 gets exactly ONE AD_Field in the whole
-- dictionary - on tab 549352. IsSelectionColumn stays 'N' and the field is IsFilterField='N', so the
-- Explicit (IncludeFiltersStrategy='E') filter set of tab 549352 is left exactly as it was.
--
-- Qualifying the host table as PP_Order.PP_Order_ID follows the sibling virtual columns on this table
-- (CostDifference, HasCostDifference, C_Order_ID), which render in this very window today.

-- 1) AD_Element -- German in the base column, en_US as the translation override.
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,Description,UpdatedBy,Created,Updated)
VALUES (0,'Y',100,'Produktionsauftrag','D','Link_PP_Order_ID',585442 /*From ID Server*/,0,
        'Produktionsauftrag',
        'Öffnet den Fertigungsauftrag im Fenster Produktionsauftrag.',
        100,
        TO_TIMESTAMP('2026-09-09 11:00:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-09 11:00:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585442
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Produktionsauftrag', PrintName='Produktionsauftrag',
       Description='Öffnet den Fertigungsauftrag im Fenster Produktionsauftrag.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-09 11:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585442 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Name='Manufacturing Order', PrintName='Manufacturing Order',
       Description='Opens the manufacturing order in the Manufacturing Order window.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-09 11:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585442 AND AD_Language='en_US'
;

-- 2) The dedicated Table reference. AD_Ref_Table.AD_Window_ID=53009 is the whole point of it.
--    Its Name is a technical identifier, not a caption: it shows only in the System-Administrator
--    "Reference" window, never in the end-user UI, so the seeded AD_Reference_Trl rows are left
--    untranslated on purpose - same as the reference this is modelled on (541143 S_Effort_Issue_ID).
--    The user-visible wording lives on AD_Element 585442 above.
INSERT INTO AD_Reference (AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,CreatedBy,UpdatedBy,Name,Description,ValidationType,EntityType,IsOrderByValue,Created,Updated)
VALUES (542138 /*From ID Server*/,0,0,'Y',100,100,
        'Link_PP_Order_ID',
        'PP_Order, gezoomt auf das Fenster Produktionsauftrag (53009).',
        'T','D','N',
        TO_TIMESTAMP('2026-09-09 11:01:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-09 11:01:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Reference_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542138
AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- AD_Key = PP_Order.PP_Order_ID (53659), AD_Display = PP_Order.DocumentNo (53621): the lookup shows
-- the document number, which is what a human recognises the order by.
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,CreatedBy,UpdatedBy,AD_Table_ID,AD_Key,AD_Display,IsValueDisplayed,ShowInactiveValues,AD_Window_ID,EntityType,Created,Updated)
VALUES (542138,0,0,'Y',100,100,
        53027,53659,53621,'N','N',
        53009,
        'D',
        TO_TIMESTAMP('2026-09-09 11:01:10','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-09 11:01:10','YYYY-MM-DD HH24:MI:SS'))
;

-- 3) The virtual column. AD_Reference_ID 30 (Search) + AD_Reference_Value_ID 542138 is the branch in
--    MLookupFactory.getLookupInfo (~L299) that routes through AD_Ref_Table, i.e. the branch that
--    picks up AD_Window_ID=53009. Search rather than Table because PP_Order is high-volume.
--    IsSelectionColumn='N' and no filter attributes: those live on AD_Column and would otherwise
--    reach every window showing PP_Order.
--    IsExcludeFromZoomTargets='Y' (the column default): this must not be offered as a
--    Related-Documents target - it is a self-reference, and being virtual it has no physical FK for
--    ad_table_related_windows_v to walk anyway.
INSERT INTO AD_Column (AD_Reference_ID,AD_Reference_Value_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,
                        AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,
                        IsEncrypted,IsExcludeFromZoomTargets,AD_Table_ID,ColumnSQL,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,
                        Name,Description,EntityType,FieldLength,Version,SeqNo,PersonalDataCategory,IsCalculated,Created,Updated)
VALUES (30,542138,'N','N','N','N',0,'Y',100,
        585442 /*From ID Server*/,'N','N','N','N','Y',
        'N','Y',53027,
        '(PP_Order.PP_Order_ID)',
        'Link_PP_Order_ID',593529 /*From ID Server*/,'N',0,100,
        'Produktionsauftrag',
        'Öffnet den Fertigungsauftrag im Fenster Produktionsauftrag.',
        'D',10,0,0,'NP','Y',
        TO_TIMESTAMP('2026-09-09 11:02:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-09 11:02:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593529
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- No AD_SQLColumn_SourceTableColumn: the expression reads only the record's own primary key, so
-- there is no other table whose change could stale the value.

-- 4) The field - on tab 549352 and nowhere else.
--    SeqNo/SeqNoGrid 15 puts it directly after DocumentNo (10) and before C_DocType_ID (20); 15 is
--    free on both the AD_Field and the AD_UI_Element grid layer.
--    IsReadOnly='Y': the monitor is read-only and the value is computed.
--    IsFilterField='N': tab 549352 runs IncludeFiltersStrategy='E', so its filter set is exactly the
--    fields flagged 'Y' - this one must not join it.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,IsFilterField,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,593529,784962 /*From ID Server*/,0,549352,
        TO_TIMESTAMP('2026-09-09 11:03:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','Y','N','N',
        'Produktionsauftrag',15,15,
        TO_TIMESTAMP('2026-09-09 11:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=784962
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(585442);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=784962;
SELECT AD_Element_Link_Create_Missing_Field(784962);

-- 5) The UI element - primary group of the left column (555514), next to DocumentNo.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784962,0,549352,555514,654728 /*From ID Server*/,'F',
        TO_TIMESTAMP('2026-09-09 11:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N',
        'Produktionsauftrag',15,15,0,
        TO_TIMESTAMP('2026-09-09 11:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585442);
