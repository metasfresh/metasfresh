-- Renumbers the Order Board window's hand-picked AD_* IDs on instances that already
-- applied the earlier OrderBoard migration scripts. Fresh installs run this as a
-- no-op (no OLD rows match).
--
-- Full-nuclear scope: every AD_* ID hand-picked in the 581036-581196 range is
-- renumbered to a fresh idserver.metas.de allocation.
--
-- Defensive matching: every renumbering requires BOTH the old numeric ID AND an
-- identifying column (ColumnName / InternalName / AD_Tab_ID+AD_Column_ID / …) so
-- an unrelated row that happens to carry the same numeric ID is NOT touched.
--
-- Two-phase design: all temp tables are populated + filtered against pristine
-- row state BEFORE any UPDATE runs. Later filters would otherwise see already-
-- renumbered FK values and fail to identify their rows.
--
-- Comprehensive FK cascade: every FK column pointing at a renumbered PK is
-- explicitly updated BEFORE the PK is renamed. This survives environments
-- where SET CONSTRAINTS ALL DEFERRED doesn't actually defer a given FK
-- (observed on a customer instance for adwindow_adwindowaccess). The DEFERRED
-- setting stays in place as a belt-and-braces guarantee.

BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- ============================================================
-- Phase 1: populate + filter temp mapping tables against pristine row state
-- ============================================================

-- --- AD_Window (identified by InternalName) ---
CREATE TEMP TABLE _ob_win_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0), internalname VARCHAR(255)) ON COMMIT DROP;
INSERT INTO _ob_win_map VALUES
    (581036, 542168, 'orderBoard');
DELETE FROM _ob_win_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Window w
                    WHERE w.AD_Window_ID  = m.old_id
                      AND w.InternalName  = m.internalname);

-- --- AD_Menu (identified by InternalName) ---
CREATE TEMP TABLE _ob_menu_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0), internalname VARCHAR(255)) ON COMMIT DROP;
INSERT INTO _ob_menu_map VALUES
    (581043, 542344, 'orderBoard');
DELETE FROM _ob_menu_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Menu u
                    WHERE u.AD_Menu_ID     = m.old_id
                      AND u.InternalName   = m.internalname);

-- --- AD_Element (identified by ColumnName; UNIQUE in ad_element) ---
CREATE TEMP TABLE _ob_elem_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0), columnname VARCHAR(255)) ON COMMIT DROP;
INSERT INTO _ob_elem_map VALUES
    (581040, 585090, 'M_Picking_OrderBoard_Wartend'),
    (581041, 585091, 'M_Picking_OrderBoard_InKommissionierung'),
    (581042, 585092, 'M_Picking_OrderBoard_Packen'),
    (581145, 585094, 'M_Picking_OrderBoard_Overview_v_ID'),
    (581146, 585095, 'QtyWaiting'),
    (581147, 585096, 'QtyPicking'),
    (581148, 585097, 'QtyPacking'),
    (581170, 585098, 'M_Picking_OrderBoard_Uebersicht');
DELETE FROM _ob_elem_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Element e
                    WHERE e.AD_Element_ID = m.old_id AND e.ColumnName = m.columnname);

-- --- AD_Table (identified by TableName; UNIQUE in ad_table) ---
CREATE TEMP TABLE _ob_tab_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0), tablename VARCHAR(60)) ON COMMIT DROP;
INSERT INTO _ob_tab_map VALUES
    (581144, 542626, 'M_Picking_OrderBoard_Overview_v');
DELETE FROM _ob_tab_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Table t
                    WHERE t.AD_Table_ID = m.old_id AND t.TableName = m.tablename);

-- --- AD_Column (identified by AD_Table_ID + ColumnName; UNIQUE per table) ---
CREATE TEMP TABLE _ob_col_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                               ad_table_id NUMERIC(10,0), columnname VARCHAR(60)) ON COMMIT DROP;
INSERT INTO _ob_col_map VALUES
    (581149, 592940, 581144, 'M_Picking_OrderBoard_Overview_v_ID'),
    (581150, 592941, 581144, 'M_Product_ID'),
    (581151, 592942, 581144, 'ProductValue'),
    (581152, 592943, 581144, 'ProductName'),
    (581153, 592944, 581144, 'C_UOM_ID'),
    (581154, 592945, 581144, 'DeliveryDate'),
    (581155, 592946, 581144, 'C_Country_ID'),
    (581156, 592947, 581144, 'CountryName'),
    (581157, 592948, 581144, 'QtyWaiting'),
    (581158, 592949, 581144, 'QtyPicking'),
    (581159, 592950, 581144, 'QtyPacking'),
    (581160, 592951, 581144, 'QtyTotal'),
    (581161, 592952, 581144, 'OrderLineCount'),
    (581162, 592953, 581144, 'AD_Client_ID'),
    (581163, 592954, 581144, 'AD_Org_ID'),
    (581164, 592955, 581144, 'Updated'),
    (581165, 592956, 581144, 'UpdatedBy'),
    (581166, 592957, 581144, 'Created'),
    (581167, 592958, 581144, 'CreatedBy'),
    (581168, 592959, 581144, 'IsActive'),
    (581169, 592960, 542622, 'M_Picking_OrderBoard_Overview_v_ID');
DELETE FROM _ob_col_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Column c
                    WHERE c.AD_Column_ID = m.old_id
                      AND c.AD_Table_ID  = m.ad_table_id
                      AND c.ColumnName   = m.columnname);

-- --- AD_Tab (identified by AD_Window_ID + AD_Element_ID; uses ORIGINAL FK IDs) ---
CREATE TEMP TABLE _ob_tab_ad_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                  ad_window_id NUMERIC(10,0), ad_element_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_tab_ad_map VALUES
    (581037, 549335, 581036, 581040),
    (581038, 549336, 581036, 581041),
    (581039, 549337, 581036, 581042),
    (581171, 549338, 581036, 581170);
DELETE FROM _ob_tab_ad_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Tab t
                    WHERE t.AD_Tab_ID     = m.old_id
                      AND t.AD_Window_ID  = m.ad_window_id
                      AND t.AD_Element_ID = m.ad_element_id);

-- --- AD_Field (identified by AD_Tab_ID + AD_Column_ID; uses ORIGINAL FK IDs) ---
CREATE TEMP TABLE _ob_field_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                 ad_tab_id NUMERIC(10,0), ad_column_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_field_map VALUES
    (581044, 781382, 581037, 592898),
    (581045, 781383, 581037, 592899),
    (581046, 781384, 581037, 592900),
    (581047, 781385, 581037, 592902),
    (581048, 781386, 581037, 592904),
    (581049, 781387, 581037, 592903),
    (581050, 781388, 581037, 592905),
    (581095, 781389, 581037, 592906),
    (581051, 781390, 581038, 592898),
    (581052, 781391, 581038, 592899),
    (581053, 781392, 581038, 592900),
    (581054, 781393, 581038, 592902),
    (581055, 781394, 581038, 592904),
    (581056, 781395, 581038, 592903),
    (581057, 781396, 581038, 592905),
    (581096, 781397, 581038, 592906),
    (581058, 781398, 581039, 592898),
    (581059, 781399, 581039, 592899),
    (581060, 781400, 581039, 592900),
    (581061, 781401, 581039, 592902),
    (581062, 781402, 581039, 592904),
    (581063, 781403, 581039, 592903),
    (581064, 781404, 581039, 592905),
    (581097, 781405, 581039, 592906),
    (581172, 781407, 581171, 581151),
    (581173, 781408, 581171, 581152),
    (581174, 781409, 581171, 581153),
    (581175, 781410, 581171, 581154),
    (581176, 781411, 581171, 581156),
    (581177, 781412, 581171, 581155),
    (581178, 781413, 581171, 581157),
    (581179, 781414, 581171, 581158),
    (581180, 781415, 581171, 581159),
    (581181, 781416, 581171, 581160),
    (581182, 781417, 581171, 581161);
DELETE FROM _ob_field_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_Field f
                    WHERE f.AD_Field_ID  = m.old_id
                      AND f.AD_Tab_ID    = m.ad_tab_id
                      AND f.AD_Column_ID = m.ad_column_id);

-- --- AD_UI_Section (identified by AD_Tab_ID; one per tab in this window) ---
CREATE TEMP TABLE _ob_uisec_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                 ad_tab_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_uisec_map VALUES
    (581065, 547844, 581037),
    (581075, 547845, 581038),
    (581085, 547846, 581039),
    (581183, 547847, 581171);
DELETE FROM _ob_uisec_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_UI_Section s
                    WHERE s.AD_UI_Section_ID = m.old_id
                      AND s.AD_Tab_ID        = m.ad_tab_id);

-- --- AD_UI_Column (identified by AD_UI_Section_ID; one per section in this window) ---
CREATE TEMP TABLE _ob_uicol_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                 ad_ui_section_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_uicol_map VALUES
    (581066, 549585, 581065),
    (581076, 549586, 581075),
    (581086, 549587, 581085),
    (581184, 549588, 581183);
DELETE FROM _ob_uicol_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_UI_Column c
                    WHERE c.AD_UI_Column_ID  = m.old_id
                      AND c.AD_UI_Section_ID = m.ad_ui_section_id);

-- --- AD_UI_ElementGroup (identified by AD_UI_Column_ID; one per column) ---
CREATE TEMP TABLE _ob_uieg_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                ad_ui_column_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_uieg_map VALUES
    (581067, 555489, 581066),
    (581077, 555490, 581076),
    (581087, 555491, 581086),
    (581185, 555492, 581184);
DELETE FROM _ob_uieg_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_UI_ElementGroup g
                    WHERE g.AD_UI_ElementGroup_ID = m.old_id
                      AND g.AD_UI_Column_ID       = m.ad_ui_column_id);

-- --- AD_UI_Element (identified by AD_Tab_ID + AD_Field_ID; uses ORIGINAL FK IDs) ---
CREATE TEMP TABLE _ob_uielem_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                  ad_tab_id NUMERIC(10,0), ad_field_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_uielem_map VALUES
    (581068, 652498, 581037, 581044),
    (581069, 652499, 581037, 581045),
    (581070, 652500, 581037, 581046),
    (581071, 652501, 581037, 581047),
    (581072, 652502, 581037, 581048),
    (581073, 652503, 581037, 581049),
    (581074, 652504, 581037, 581050),
    (581098, 652505, 581037, 581095),
    (581078, 652506, 581038, 581051),
    (581079, 652507, 581038, 581052),
    (581080, 652508, 581038, 581053),
    (581081, 652509, 581038, 581054),
    (581082, 652510, 581038, 581055),
    (581083, 652511, 581038, 581056),
    (581084, 652512, 581038, 581057),
    (581099, 652513, 581038, 581096),
    (581088, 652514, 581039, 581058),
    (581089, 652515, 581039, 581059),
    (581090, 652516, 581039, 581060),
    (581091, 652517, 581039, 581061),
    (581092, 652518, 581039, 581062),
    (581093, 652519, 581039, 581063),
    (581094, 652520, 581039, 581064),
    (581100, 652521, 581039, 581097),
    (581186, 652523, 581171, 581172),
    (581187, 652524, 581171, 581173),
    (581188, 652525, 581171, 581174),
    (581189, 652526, 581171, 581175),
    (581190, 652527, 581171, 581176),
    (581191, 652528, 581171, 581177),
    (581192, 652529, 581171, 581178),
    (581193, 652530, 581171, 581179),
    (581194, 652531, 581171, 581180),
    (581195, 652532, 581171, 581181),
    (581196, 652533, 581171, 581182);
DELETE FROM _ob_uielem_map m
 WHERE NOT EXISTS (SELECT 1 FROM AD_UI_Element u
                    WHERE u.AD_UI_Element_ID = m.old_id
                      AND u.AD_Tab_ID        = m.ad_tab_id
                      AND u.AD_Field_ID      = m.ad_field_id);

-- ============================================================
-- Phase 2: cascade every FK, then rename each PK
-- Order: AD_Element -> AD_Window -> AD_Menu -> AD_Table -> AD_Column
--     -> AD_Tab -> AD_Field -> AD_UI_Section -> AD_UI_Column
--     -> AD_UI_ElementGroup -> AD_UI_Element
-- ============================================================

-- AD_Element cascades + PK
UPDATE ad_column t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_element_link t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_element_trl t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_field t SET ad_name_id = m.new_id FROM _ob_elem_map m WHERE t.ad_name_id = m.old_id;
UPDATE ad_infocolumn t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_menu t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_process_para t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_tab t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE ad_window t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE webui_kpi_field t SET ad_element_id = m.new_id FROM _ob_elem_map m WHERE t.ad_element_id = m.old_id;
UPDATE AD_Element e SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE e.AD_Element_ID = m.old_id;

-- AD_Window cascades + PK
UPDATE ad_element_link t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_issue t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_menu t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_note t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_pinstance t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_preference t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_ref_table t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_role_permrequest t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_searchdefinition t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_searchdefinition t SET po_window_id = m.new_id FROM _ob_win_map m WHERE t.po_window_id = m.old_id;
UPDATE ad_tab t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_table t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_table t SET po_window_id = m.new_id FROM _ob_win_map m WHERE t.po_window_id = m.old_id;
UPDATE ad_user_sortpref_hdr t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_userdef_win t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_wf_node t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_window t SET overrides_window_id = m.new_id FROM _ob_win_map m WHERE t.overrides_window_id = m.old_id;
UPDATE ad_window_access t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_window_trl t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE ad_workbenchwindow t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE dataentry_tab t SET dataentry_targetwindow_id = m.new_id FROM _ob_win_map m WHERE t.dataentry_targetwindow_id = m.old_id;
UPDATE i_dataentry_record t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE pa_dashboardcontent t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE webui_kpi t SET ad_window_id = m.new_id FROM _ob_win_map m WHERE t.ad_window_id = m.old_id;
UPDATE AD_Window w SET AD_Window_ID = m.new_id FROM _ob_win_map m WHERE w.AD_Window_ID = m.old_id;

-- AD_Menu cascades + PK
UPDATE ad_menu_trl t SET ad_menu_id = m.new_id FROM _ob_menu_map m WHERE t.ad_menu_id = m.old_id;
UPDATE ad_role t SET root_menu_id = m.new_id FROM _ob_menu_map m WHERE t.root_menu_id = m.old_id;
UPDATE AD_TreeNodeMM t SET Node_ID   = m.new_id FROM _ob_menu_map m WHERE t.Node_ID   = m.old_id;
UPDATE AD_TreeNodeMM t SET Parent_ID = m.new_id FROM _ob_menu_map m WHERE t.Parent_ID = m.old_id;
UPDATE AD_Menu       u SET AD_Menu_ID = m.new_id FROM _ob_menu_map m WHERE u.AD_Menu_ID = m.old_id;

-- AD_Table cascades + PK
UPDATE ad_accesslog t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_alertrule t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_archive t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_attachment t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_attachment_log t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_attachment_multiref t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_attachmententry t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_attribute t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_clientshare t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_column t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_column_access t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_columncallout t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_field_contextmenu t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_housekeeping t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_impformat t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_index_table t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_infowindow t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_migrationstep t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_note t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_org_mapping t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_printerrouting t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_printformat t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_printlabel t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_private_access t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_ref_table t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_replicationdocument t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_replicationtable t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_reportview t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_role_record_access_config t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_searchdefinition t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_sequence_audit t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_sqlcolumn_sourcetablecolumn t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_sqlcolumn_sourcetablecolumn t SET source_table_id = m.new_id FROM _ob_tab_map m WHERE t.source_table_id = m.old_id;
UPDATE ad_tab t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_table_access t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_table_attachmentlistener t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_table_mview t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_table_process t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_table_scriptvalidator t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_table_trl t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_tree t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_triggerui t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_user_record_access t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_userquery t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_val_rule_dep t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_wf_activity t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_wf_eventaudit t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_wf_process t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE ad_workflow t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE api_request_audit_log t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE c_bp_printformat t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE c_doc_outbound_config t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE c_doc_outbound_log t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE c_doc_outbound_log_line t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE c_doc_responsible t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE cm_chat t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE cm_chattype t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE cm_templatetable t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE cm_wikitoken t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE dataentry_record t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE es_fts_config_sourcemodel t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE es_fts_filter t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE es_fts_index_queue t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE exp_format t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE exp_replicationtrxline t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE externalsystem_exportaudit t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE fact_acct t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE hr_allocationline t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE i_dataentry_record t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE k_index t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE m_hu_assignment t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE m_hu_trx_line t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE m_material_tracking_ref t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE m_shipment_constraint t SET sourcedoc_table_id = m.new_id FROM _ob_tab_map m WHERE t.sourcedoc_table_id = m.old_id;
UPDATE pa_measurecalc t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE pa_sla_measure t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE r_request t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE s_externalreference t SET referenced_ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.referenced_ad_table_id = m.old_id;
UPDATE webui_board t SET ad_table_id = m.new_id FROM _ob_tab_map m WHERE t.ad_table_id = m.old_id;
UPDATE webui_kpi t SET source_table_id = m.new_id FROM _ob_tab_map m WHERE t.source_table_id = m.old_id;
UPDATE AD_Table t SET AD_Table_ID = m.new_id FROM _ob_tab_map m WHERE t.AD_Table_ID = m.old_id;

-- AD_Column cascades + PK
UPDATE ad_accesslog t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_column_access t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_column_trl t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_columncallout t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_field t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_field_contextmenu t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_find t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_impformat_row t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_index_column t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_migrationdata t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_printformatitem t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_printlabelline t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_ref_table t SET ad_display = m.new_id FROM _ob_col_map m WHERE t.ad_display = m.old_id;
UPDATE ad_ref_table t SET ad_key = m.new_id FROM _ob_col_map m WHERE t.ad_key = m.old_id;
UPDATE ad_reportview_col t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_searchdefinition t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_sqlcolumn_sourcetablecolumn t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_sqlcolumn_sourcetablecolumn t SET link_column_id = m.new_id FROM _ob_col_map m WHERE t.link_column_id = m.old_id;
UPDATE ad_sqlcolumn_sourcetablecolumn t SET source_column_id = m.new_id FROM _ob_col_map m WHERE t.source_column_id = m.old_id;
UPDATE ad_tab t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_tab t SET ad_columnsortorder_id = m.new_id FROM _ob_col_map m WHERE t.ad_columnsortorder_id = m.old_id;
UPDATE ad_tab t SET ad_columnsortyesno_id = m.new_id FROM _ob_col_map m WHERE t.ad_columnsortyesno_id = m.old_id;
UPDATE ad_tab t SET parent_column_id = m.new_id FROM _ob_col_map m WHERE t.parent_column_id = m.old_id;
UPDATE ad_triggerui_action t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_triggerui_criteria t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_wf_nextcondition t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_wf_node t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE ad_workbench t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE c_acctschema_element t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE c_advcommissionrelevantpo t SET bpartnercolumn_id = m.new_id FROM _ob_col_map m WHERE t.bpartnercolumn_id = m.old_id;
UPDATE c_advcommissionrelevantpo t SET datedoccolumn_id = m.new_id FROM _ob_col_map m WHERE t.datedoccolumn_id = m.old_id;
UPDATE c_aggregationitem t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE c_olcandaggandorder t SET ad_column_olcand_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_olcand_id = m.old_id;
UPDATE datev_exportformatcolumn t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE dim_dimension_spec_assignment t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE dlm_partition_config_reference t SET dlm_referencing_column_id = m.new_id FROM _ob_col_map m WHERE t.dlm_referencing_column_id = m.old_id;
UPDATE es_fts_config_sourcemodel t SET parent_column_id = m.new_id FROM _ob_col_map m WHERE t.parent_column_id = m.old_id;
UPDATE es_fts_filter_joincolumn t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE exp_formatline t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE i_elementvalue t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE webui_board_cardfield t SET ad_column_id = m.new_id FROM _ob_col_map m WHERE t.ad_column_id = m.old_id;
UPDATE AD_Column c SET AD_Column_ID = m.new_id FROM _ob_col_map m WHERE c.AD_Column_ID = m.old_id;

-- AD_Tab cascades + PK
UPDATE ad_element_link t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_field t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_field t SET included_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.included_tab_id = m.old_id;
UPDATE ad_tab t SET included_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.included_tab_id = m.old_id;
UPDATE ad_tab t SET template_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.template_tab_id = m.old_id;
UPDATE ad_tab_callout t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_tab_trl t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_table_process t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_triggerui t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_ui_element t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_ui_element t SET inline_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.inline_tab_id = m.old_id;
UPDATE ad_ui_element t SET labels_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.labels_tab_id = m.old_id;
UPDATE ad_ui_section t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_user_sortpref_hdr t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_userdef_tab t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE ad_userquery t SET ad_tab_id = m.new_id FROM _ob_tab_ad_map m WHERE t.ad_tab_id = m.old_id;
UPDATE AD_Tab t SET AD_Tab_ID = m.new_id FROM _ob_tab_ad_map m WHERE t.AD_Tab_ID = m.old_id;

-- AD_Field cascades + PK
UPDATE ad_element_link t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE ad_field_contextmenu t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE ad_field_trl t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE ad_ui_element t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE ad_ui_element t SET labels_selector_field_id = m.new_id FROM _ob_field_map m WHERE t.labels_selector_field_id = m.old_id;
UPDATE ad_ui_elementfield t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE ad_user_sortpref_line t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE ad_userdef_field t SET ad_field_id = m.new_id FROM _ob_field_map m WHERE t.ad_field_id = m.old_id;
UPDATE AD_Field f SET AD_Field_ID = m.new_id FROM _ob_field_map m WHERE f.AD_Field_ID = m.old_id;

-- AD_UI_Section cascades + PK
UPDATE ad_ui_column t SET ad_ui_section_id = m.new_id FROM _ob_uisec_map m WHERE t.ad_ui_section_id = m.old_id;
UPDATE ad_ui_section_trl t SET ad_ui_section_id = m.new_id FROM _ob_uisec_map m WHERE t.ad_ui_section_id = m.old_id;
UPDATE AD_UI_Section s SET AD_UI_Section_ID = m.new_id FROM _ob_uisec_map m WHERE s.AD_UI_Section_ID = m.old_id;

-- AD_UI_Column cascades + PK
UPDATE ad_ui_elementgroup t SET ad_ui_column_id = m.new_id FROM _ob_uicol_map m WHERE t.ad_ui_column_id = m.old_id;
UPDATE AD_UI_Column c SET AD_UI_Column_ID = m.new_id FROM _ob_uicol_map m WHERE c.AD_UI_Column_ID = m.old_id;

-- AD_UI_ElementGroup cascades + PK
UPDATE ad_ui_element t SET ad_ui_elementgroup_id = m.new_id FROM _ob_uieg_map m WHERE t.ad_ui_elementgroup_id = m.old_id;
UPDATE AD_UI_ElementGroup g SET AD_UI_ElementGroup_ID = m.new_id FROM _ob_uieg_map m WHERE g.AD_UI_ElementGroup_ID = m.old_id;

-- AD_UI_Element cascades + PK
UPDATE ad_ui_elementfield t SET ad_ui_element_id = m.new_id FROM _ob_uielem_map m WHERE t.ad_ui_element_id = m.old_id;
UPDATE AD_UI_Element u SET AD_UI_Element_ID = m.new_id FROM _ob_uielem_map m WHERE u.AD_UI_Element_ID = m.old_id;

COMMIT;
