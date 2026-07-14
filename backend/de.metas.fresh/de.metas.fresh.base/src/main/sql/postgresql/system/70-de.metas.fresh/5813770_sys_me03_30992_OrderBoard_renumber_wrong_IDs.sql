-- Renumbers the Order Board window's hand-picked AD_Element, AD_Field, AD_UI_Element
-- and AD_Column IDs on instances that already applied the earlier OrderBoard migration
-- scripts. Fresh installs run this as a no-op (no OLD rows match).
--
-- Scope: only ID types whose OLD values sit BEHIND the current idserver.metas.de
-- sequence and can therefore collide with existing rows on the target instance.
-- Kept as-is (server sequence still below these): AD_Window, AD_Tab, AD_Menu,
-- AD_UI_Section, AD_UI_Column, AD_UI_ElementGroup, AD_Table.
--
-- Defensive matching: for every renumbering we require BOTH the old numeric ID
-- AND an identifying column (ColumnName / AD_Tab_ID+AD_Column_ID / …) so that
-- an unrelated row that happens to carry the same numeric ID on some instance
-- is NOT touched.
--
-- Two-phase design: all temp tables are populated and filtered BEFORE any UPDATE
-- runs. Otherwise a later filter (e.g. AD_Field's AD_Column_ID discriminator)
-- would see already-renumbered FK values from an earlier phase.
--
-- Wrapped in a single transaction with SET CONSTRAINTS ALL DEFERRED (metasfresh
-- AD FKs are DEFERRABLE INITIALLY DEFERRED); FK integrity is validated at COMMIT.

BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- ============================================================
-- Phase 1: populate + filter temp mapping tables against pristine row state
-- ============================================================

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

-- --- AD_Field (identified by AD_Tab_ID + AD_Column_ID; uses ORIGINAL ad_column_id) ---
CREATE TEMP TABLE _ob_field_map (old_id NUMERIC(10,0), new_id NUMERIC(10,0),
                                 ad_tab_id NUMERIC(10,0), ad_column_id NUMERIC(10,0)) ON COMMIT DROP;
INSERT INTO _ob_field_map VALUES
-- Tab 1 Wartend (581037), columns 592898-906 from 5809900 (kept)
    (581044, 781382, 581037, 592898),
    (581045, 781383, 581037, 592899),
    (581046, 781384, 581037, 592900),
    (581047, 781385, 581037, 592902),
    (581048, 781386, 581037, 592904),
    (581049, 781387, 581037, 592903),
    (581050, 781388, 581037, 592905),
    (581095, 781389, 581037, 592906),
-- Tab 2 In Kommissionierung (581038)
    (581051, 781390, 581038, 592898),
    (581052, 781391, 581038, 592899),
    (581053, 781392, 581038, 592900),
    (581054, 781393, 581038, 592902),
    (581055, 781394, 581038, 592904),
    (581056, 781395, 581038, 592903),
    (581057, 781396, 581038, 592905),
    (581096, 781397, 581038, 592906),
-- Tab 3 Packen (581039)
    (581058, 781398, 581039, 592898),
    (581059, 781399, 581039, 592899),
    (581060, 781400, 581039, 592900),
    (581061, 781401, 581039, 592902),
    (581062, 781402, 581039, 592904),
    (581063, 781403, 581039, 592903),
    (581064, 781404, 581039, 592905),
    (581097, 781405, 581039, 592906),
-- Overview tab (581171) — columns still at their OLD ids in pristine state
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

-- --- AD_UI_Element (identified by AD_Tab_ID + AD_Field_ID; uses ORIGINAL ad_field_id) ---
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
-- Phase 2: apply UPDATEs using the settled mappings
-- ============================================================

-- --- AD_Element cascades + PK ---
UPDATE AD_Element_Trl t SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE t.AD_Element_ID = m.old_id;
UPDATE AD_Column      c SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE c.AD_Element_ID = m.old_id;
UPDATE AD_Tab         t SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE t.AD_Element_ID = m.old_id;
UPDATE AD_Window      w SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE w.AD_Element_ID = m.old_id;
UPDATE AD_Menu        u SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE u.AD_Element_ID = m.old_id;
UPDATE AD_Field       f SET AD_Name_ID    = m.new_id FROM _ob_elem_map m WHERE f.AD_Name_ID    = m.old_id;
UPDATE AD_Element_Link l SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE l.AD_Element_ID = m.old_id;
UPDATE AD_Element     e SET AD_Element_ID = m.new_id FROM _ob_elem_map m WHERE e.AD_Element_ID = m.old_id;

-- --- AD_Column cascades + PK ---
UPDATE AD_Column_Trl t SET AD_Column_ID     = m.new_id FROM _ob_col_map m WHERE t.AD_Column_ID     = m.old_id;
UPDATE AD_Field      f SET AD_Column_ID     = m.new_id FROM _ob_col_map m WHERE f.AD_Column_ID     = m.old_id;
UPDATE AD_Tab        t SET AD_Column_ID     = m.new_id FROM _ob_col_map m WHERE t.AD_Column_ID     = m.old_id;
UPDATE AD_Tab        t SET Parent_Column_ID = m.new_id FROM _ob_col_map m WHERE t.Parent_Column_ID = m.old_id;
UPDATE AD_Column     c SET AD_Column_ID     = m.new_id FROM _ob_col_map m WHERE c.AD_Column_ID     = m.old_id;

-- --- AD_Field cascades + PK ---
UPDATE AD_Field_Trl    t SET AD_Field_ID              = m.new_id FROM _ob_field_map m WHERE t.AD_Field_ID              = m.old_id;
UPDATE AD_UI_Element   u SET AD_Field_ID              = m.new_id FROM _ob_field_map m WHERE u.AD_Field_ID              = m.old_id;
UPDATE AD_UI_Element   u SET Labels_Selector_Field_ID = m.new_id FROM _ob_field_map m WHERE u.Labels_Selector_Field_ID = m.old_id;
UPDATE AD_Element_Link l SET AD_Field_ID              = m.new_id FROM _ob_field_map m WHERE l.AD_Field_ID              = m.old_id;
UPDATE AD_Field        f SET AD_Field_ID              = m.new_id FROM _ob_field_map m WHERE f.AD_Field_ID              = m.old_id;

-- --- AD_UI_Element PK (leaf, no _Trl table in metasfresh schema) ---
UPDATE AD_UI_Element u SET AD_UI_Element_ID = m.new_id FROM _ob_uielem_map m WHERE u.AD_UI_Element_ID = m.old_id;

COMMIT;
