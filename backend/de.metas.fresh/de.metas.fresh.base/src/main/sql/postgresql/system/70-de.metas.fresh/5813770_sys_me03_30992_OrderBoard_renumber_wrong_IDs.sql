-- Renumbers the Order Board window's hand-picked AD_* IDs on instances that already
-- applied the earlier OrderBoard migration scripts. Fresh installs run this as a
-- no-op (no OLD rows match).
--
-- Full-nuclear scope: every AD_* ID hand-picked in the 581036-581196 range is
-- renumbered to a fresh idserver.metas.de allocation. Every UPDATE carries the
-- identifying columns INLINE so unrelated rows on the target instance that
-- happen to carry the same numeric ID are NOT touched.
--
-- Pattern (per Cristina's example):
--   UPDATE ad_ui_element SET ad_ui_element_id = 652498
--     WHERE ad_ui_element_id = 581068
--       AND ad_tab_id = 549335 AND ad_field_id = 781382;
-- For FK cascades the identity is checked via an EXISTS subquery on the parent.
--
-- Order: types are processed parent-before-child. The discriminator for a
-- later block references the ALREADY-renamed parent id (e.g. AD_Tab is
-- identified by AD_Window_ID = 542168 by the time we reach it) because the
-- earlier block has already run in the same transaction.
--
-- Wrapped in one transaction. SET CONSTRAINTS ALL DEFERRED stays as a
-- belt-and-braces backstop, but every FK is also explicitly cascaded.

BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- ad_window 581036 -> 542168 : internalname = 'orderBoard'
UPDATE ad_element_link SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_issue SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_menu SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_note SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_pinstance SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_preference SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_ref_table SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_role_permrequest SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_searchdefinition SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_searchdefinition SET po_window_id = 542168
  WHERE po_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_tab SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_table SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_table SET po_window_id = 542168
  WHERE po_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_user_sortpref_hdr SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_userdef_win SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_wf_node SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_window SET overrides_window_id = 542168
  WHERE overrides_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_window_access SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_window_trl SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_workbenchwindow SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE dataentry_tab SET dataentry_targetwindow_id = 542168
  WHERE dataentry_targetwindow_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE i_dataentry_record SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE pa_dashboardcontent SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE webui_kpi SET ad_window_id = 542168
  WHERE ad_window_id = 581036
    AND EXISTS (SELECT 1 FROM ad_window p WHERE p.ad_window_id = 581036 AND p.internalname = 'orderBoard');
UPDATE ad_window SET ad_window_id = 542168 WHERE ad_window_id = 581036 AND internalname = 'orderBoard';

-- ad_menu 581043 -> 542344 : internalname = 'orderBoard'
UPDATE ad_menu_trl SET ad_menu_id = 542344
  WHERE ad_menu_id = 581043
    AND EXISTS (SELECT 1 FROM ad_menu p WHERE p.ad_menu_id = 581043 AND p.internalname = 'orderBoard');
UPDATE ad_role SET root_menu_id = 542344
  WHERE root_menu_id = 581043
    AND EXISTS (SELECT 1 FROM ad_menu p WHERE p.ad_menu_id = 581043 AND p.internalname = 'orderBoard');
UPDATE ad_treenodemm SET node_id = 542344
  WHERE node_id = 581043
    AND EXISTS (SELECT 1 FROM ad_menu p WHERE p.ad_menu_id = 581043 AND p.internalname = 'orderBoard');
UPDATE ad_treenodemm SET parent_id = 542344
  WHERE parent_id = 581043
    AND EXISTS (SELECT 1 FROM ad_menu p WHERE p.ad_menu_id = 581043 AND p.internalname = 'orderBoard');
UPDATE ad_menu SET ad_menu_id = 542344 WHERE ad_menu_id = 581043 AND internalname = 'orderBoard';

-- ad_element 581040 -> 585090 : columnname = 'M_Picking_OrderBoard_Wartend'
UPDATE ad_column SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_element_link SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_element_trl SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_field SET ad_name_id = 585090
  WHERE ad_name_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_infocolumn SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_menu SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_process_para SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_tab SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_window SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE webui_kpi_field SET ad_element_id = 585090
  WHERE ad_element_id = 581040
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581040 AND p.columnname = 'M_Picking_OrderBoard_Wartend');
UPDATE ad_element SET ad_element_id = 585090 WHERE ad_element_id = 581040 AND columnname = 'M_Picking_OrderBoard_Wartend';

-- ad_element 581041 -> 585091 : columnname = 'M_Picking_OrderBoard_InKommissionierung'
UPDATE ad_column SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_element_link SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_element_trl SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_field SET ad_name_id = 585091
  WHERE ad_name_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_infocolumn SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_menu SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_process_para SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_tab SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_window SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE webui_kpi_field SET ad_element_id = 585091
  WHERE ad_element_id = 581041
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581041 AND p.columnname = 'M_Picking_OrderBoard_InKommissionierung');
UPDATE ad_element SET ad_element_id = 585091 WHERE ad_element_id = 581041 AND columnname = 'M_Picking_OrderBoard_InKommissionierung';

-- ad_element 581042 -> 585092 : columnname = 'M_Picking_OrderBoard_Packen'
UPDATE ad_column SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_element_link SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_element_trl SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_field SET ad_name_id = 585092
  WHERE ad_name_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_infocolumn SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_menu SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_process_para SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_tab SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_window SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE webui_kpi_field SET ad_element_id = 585092
  WHERE ad_element_id = 581042
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581042 AND p.columnname = 'M_Picking_OrderBoard_Packen');
UPDATE ad_element SET ad_element_id = 585092 WHERE ad_element_id = 581042 AND columnname = 'M_Picking_OrderBoard_Packen';

-- ad_element 581145 -> 585094 : columnname = 'M_Picking_OrderBoard_Overview_v_ID'
UPDATE ad_column SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_element_link SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_element_trl SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_field SET ad_name_id = 585094
  WHERE ad_name_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_infocolumn SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_menu SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_process_para SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_window SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE webui_kpi_field SET ad_element_id = 585094
  WHERE ad_element_id = 581145
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581145 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_element SET ad_element_id = 585094 WHERE ad_element_id = 581145 AND columnname = 'M_Picking_OrderBoard_Overview_v_ID';

-- ad_element 581146 -> 585095 : columnname = 'QtyWaiting'
UPDATE ad_column SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_element_link SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_element_trl SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_field SET ad_name_id = 585095
  WHERE ad_name_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_infocolumn SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_menu SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_process_para SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_tab SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_window SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE webui_kpi_field SET ad_element_id = 585095
  WHERE ad_element_id = 581146
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581146 AND p.columnname = 'QtyWaiting');
UPDATE ad_element SET ad_element_id = 585095 WHERE ad_element_id = 581146 AND columnname = 'QtyWaiting';

-- ad_element 581147 -> 585096 : columnname = 'QtyPicking'
UPDATE ad_column SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_element_link SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_element_trl SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_field SET ad_name_id = 585096
  WHERE ad_name_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_infocolumn SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_menu SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_process_para SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_tab SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_window SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE webui_kpi_field SET ad_element_id = 585096
  WHERE ad_element_id = 581147
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581147 AND p.columnname = 'QtyPicking');
UPDATE ad_element SET ad_element_id = 585096 WHERE ad_element_id = 581147 AND columnname = 'QtyPicking';

-- ad_element 581148 -> 585097 : columnname = 'QtyPacking'
UPDATE ad_column SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_element_link SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_element_trl SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_field SET ad_name_id = 585097
  WHERE ad_name_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_infocolumn SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_menu SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_process_para SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_tab SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_window SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE webui_kpi_field SET ad_element_id = 585097
  WHERE ad_element_id = 581148
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581148 AND p.columnname = 'QtyPacking');
UPDATE ad_element SET ad_element_id = 585097 WHERE ad_element_id = 581148 AND columnname = 'QtyPacking';

-- ad_element 581170 -> 585098 : columnname = 'M_Picking_OrderBoard_Uebersicht'
UPDATE ad_column SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_element_link SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_element_trl SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_field SET ad_name_id = 585098
  WHERE ad_name_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_infocolumn SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_menu SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_process_para SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_tab SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_window SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE webui_kpi_field SET ad_element_id = 585098
  WHERE ad_element_id = 581170
    AND EXISTS (SELECT 1 FROM ad_element p WHERE p.ad_element_id = 581170 AND p.columnname = 'M_Picking_OrderBoard_Uebersicht');
UPDATE ad_element SET ad_element_id = 585098 WHERE ad_element_id = 581170 AND columnname = 'M_Picking_OrderBoard_Uebersicht';

-- ad_table 581144 -> 542626 : tablename = 'M_Picking_OrderBoard_Overview_v'
UPDATE ad_accesslog SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');

UPDATE ad_column SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_column_access SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_columncallout SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_field_contextmenu SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_housekeeping SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_impformat SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_index_table SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_infowindow SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_note SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_org_mapping SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_printerrouting SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_printformat SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_printlabel SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_private_access SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_ref_table SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_replicationdocument SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_replicationtable SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_reportview SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_role_record_access_config SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_searchdefinition SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_sequence_audit SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_table_id = 542626
  WHERE source_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_tab SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_table_access SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_table_attachmentlistener SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_table_mview SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_table_process SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_table_scriptvalidator SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_table_trl SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_tree SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_triggerui SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_user_record_access SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_userquery SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_val_rule_dep SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_wf_activity SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_wf_eventaudit SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_wf_process SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE ad_workflow SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');

UPDATE c_bp_printformat SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE c_doc_outbound_config SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE c_doc_outbound_log SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE c_doc_outbound_log_line SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');
UPDATE c_doc_responsible SET ad_table_id = 542626
  WHERE ad_table_id = 581144
    AND EXISTS (SELECT 1 FROM ad_table p WHERE p.ad_table_id = 581144 AND p.tablename = 'M_Picking_OrderBoard_Overview_v');

UPDATE ad_table SET ad_table_id = 542626 WHERE ad_table_id = 581144 AND tablename = 'M_Picking_OrderBoard_Overview_v';

-- ad_column 581149 -> 592940 : ad_table_id = 542626 AND columnname = 'M_Picking_OrderBoard_Overview_v_ID'
UPDATE ad_accesslog SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_column_access SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_column_trl SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_columncallout SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_field SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_find SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_impformat_row SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_index_column SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_migrationdata SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_printformatitem SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_printlabelline SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_ref_table SET ad_display = 592940
  WHERE ad_display = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_ref_table SET ad_key = 592940
  WHERE ad_key = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_reportview_col SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592940
  WHERE link_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592940
  WHERE source_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592940
  WHERE ad_columnsortorder_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592940
  WHERE ad_columnsortyesno_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET parent_column_id = 592940
  WHERE parent_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_wf_node SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_workbench SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_acctschema_element SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592940
  WHERE bpartnercolumn_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592940
  WHERE datedoccolumn_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_aggregationitem SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592940
  WHERE ad_column_olcand_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592940
  WHERE dlm_referencing_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592940
  WHERE parent_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE exp_formatline SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE i_elementvalue SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592940
  WHERE ad_column_id = 581149
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581149 AND p.ad_table_id = 542626 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_column SET ad_column_id = 592940 WHERE ad_column_id = 581149 AND ad_table_id = 542626 AND columnname = 'M_Picking_OrderBoard_Overview_v_ID';

-- ad_column 581150 -> 592941 : ad_table_id = 542626 AND columnname = 'M_Product_ID'
UPDATE ad_accesslog SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_column_access SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_column_trl SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_columncallout SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_field SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_find SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_impformat_row SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_index_column SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_migrationdata SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_printformatitem SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_printlabelline SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_ref_table SET ad_display = 592941
  WHERE ad_display = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_ref_table SET ad_key = 592941
  WHERE ad_key = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_reportview_col SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592941
  WHERE link_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592941
  WHERE source_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_tab SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592941
  WHERE ad_columnsortorder_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592941
  WHERE ad_columnsortyesno_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_tab SET parent_column_id = 592941
  WHERE parent_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_wf_node SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_workbench SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE c_acctschema_element SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592941
  WHERE bpartnercolumn_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592941
  WHERE datedoccolumn_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE c_aggregationitem SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592941
  WHERE ad_column_olcand_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592941
  WHERE dlm_referencing_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592941
  WHERE parent_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE exp_formatline SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE i_elementvalue SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592941
  WHERE ad_column_id = 581150
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581150 AND p.ad_table_id = 542626 AND p.columnname = 'M_Product_ID');
UPDATE ad_column SET ad_column_id = 592941 WHERE ad_column_id = 581150 AND ad_table_id = 542626 AND columnname = 'M_Product_ID';

-- ad_column 581151 -> 592942 : ad_table_id = 542626 AND columnname = 'ProductValue'
UPDATE ad_accesslog SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_column_access SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_column_trl SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_columncallout SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_field SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_field_contextmenu SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_find SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_impformat_row SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_index_column SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_migrationdata SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_printformatitem SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_printlabelline SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_ref_table SET ad_display = 592942
  WHERE ad_display = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_ref_table SET ad_key = 592942
  WHERE ad_key = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_reportview_col SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_searchdefinition SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592942
  WHERE link_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592942
  WHERE source_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_tab SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_tab SET ad_columnsortorder_id = 592942
  WHERE ad_columnsortorder_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_tab SET ad_columnsortyesno_id = 592942
  WHERE ad_columnsortyesno_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_tab SET parent_column_id = 592942
  WHERE parent_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_triggerui_action SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_triggerui_criteria SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_wf_nextcondition SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_wf_node SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_workbench SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE c_acctschema_element SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592942
  WHERE bpartnercolumn_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592942
  WHERE datedoccolumn_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE c_aggregationitem SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592942
  WHERE ad_column_olcand_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE datev_exportformatcolumn SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592942
  WHERE dlm_referencing_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592942
  WHERE parent_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE exp_formatline SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE i_elementvalue SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE webui_board_cardfield SET ad_column_id = 592942
  WHERE ad_column_id = 581151
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581151 AND p.ad_table_id = 542626 AND p.columnname = 'ProductValue');
UPDATE ad_column SET ad_column_id = 592942 WHERE ad_column_id = 581151 AND ad_table_id = 542626 AND columnname = 'ProductValue';

-- ad_column 581152 -> 592943 : ad_table_id = 542626 AND columnname = 'ProductName'
UPDATE ad_accesslog SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_column_access SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_column_trl SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_columncallout SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_field SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_field_contextmenu SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_find SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_impformat_row SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_index_column SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_migrationdata SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_printformatitem SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_printlabelline SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_ref_table SET ad_display = 592943
  WHERE ad_display = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_ref_table SET ad_key = 592943
  WHERE ad_key = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_reportview_col SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_searchdefinition SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592943
  WHERE link_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592943
  WHERE source_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_tab SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_tab SET ad_columnsortorder_id = 592943
  WHERE ad_columnsortorder_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_tab SET ad_columnsortyesno_id = 592943
  WHERE ad_columnsortyesno_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_tab SET parent_column_id = 592943
  WHERE parent_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_triggerui_action SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_triggerui_criteria SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_wf_nextcondition SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_wf_node SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_workbench SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE c_acctschema_element SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592943
  WHERE bpartnercolumn_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592943
  WHERE datedoccolumn_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE c_aggregationitem SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592943
  WHERE ad_column_olcand_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE datev_exportformatcolumn SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592943
  WHERE dlm_referencing_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592943
  WHERE parent_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE exp_formatline SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE i_elementvalue SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE webui_board_cardfield SET ad_column_id = 592943
  WHERE ad_column_id = 581152
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581152 AND p.ad_table_id = 542626 AND p.columnname = 'ProductName');
UPDATE ad_column SET ad_column_id = 592943 WHERE ad_column_id = 581152 AND ad_table_id = 542626 AND columnname = 'ProductName';

-- ad_column 581153 -> 592944 : ad_table_id = 542626 AND columnname = 'C_UOM_ID'
UPDATE ad_accesslog SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_column_access SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_column_trl SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_columncallout SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_field SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_find SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_impformat_row SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_index_column SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_migrationdata SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_printformatitem SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_printlabelline SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_ref_table SET ad_display = 592944
  WHERE ad_display = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_ref_table SET ad_key = 592944
  WHERE ad_key = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_reportview_col SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592944
  WHERE link_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592944
  WHERE source_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_tab SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592944
  WHERE ad_columnsortorder_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592944
  WHERE ad_columnsortyesno_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_tab SET parent_column_id = 592944
  WHERE parent_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_wf_node SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_workbench SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE c_acctschema_element SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592944
  WHERE bpartnercolumn_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592944
  WHERE datedoccolumn_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE c_aggregationitem SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592944
  WHERE ad_column_olcand_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592944
  WHERE dlm_referencing_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592944
  WHERE parent_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE exp_formatline SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE i_elementvalue SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592944
  WHERE ad_column_id = 581153
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581153 AND p.ad_table_id = 542626 AND p.columnname = 'C_UOM_ID');
UPDATE ad_column SET ad_column_id = 592944 WHERE ad_column_id = 581153 AND ad_table_id = 542626 AND columnname = 'C_UOM_ID';

-- ad_column 581154 -> 592945 : ad_table_id = 542626 AND columnname = 'DeliveryDate'
UPDATE ad_accesslog SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_column_access SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_column_trl SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_columncallout SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_field SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_field_contextmenu SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_find SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_impformat_row SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_index_column SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_migrationdata SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_printformatitem SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_printlabelline SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_ref_table SET ad_display = 592945
  WHERE ad_display = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_ref_table SET ad_key = 592945
  WHERE ad_key = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_reportview_col SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_searchdefinition SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592945
  WHERE link_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592945
  WHERE source_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_tab SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_tab SET ad_columnsortorder_id = 592945
  WHERE ad_columnsortorder_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_tab SET ad_columnsortyesno_id = 592945
  WHERE ad_columnsortyesno_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_tab SET parent_column_id = 592945
  WHERE parent_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_triggerui_action SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_triggerui_criteria SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_wf_nextcondition SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_wf_node SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_workbench SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE c_acctschema_element SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592945
  WHERE bpartnercolumn_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592945
  WHERE datedoccolumn_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE c_aggregationitem SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592945
  WHERE ad_column_olcand_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE datev_exportformatcolumn SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592945
  WHERE dlm_referencing_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592945
  WHERE parent_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE exp_formatline SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE i_elementvalue SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE webui_board_cardfield SET ad_column_id = 592945
  WHERE ad_column_id = 581154
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581154 AND p.ad_table_id = 542626 AND p.columnname = 'DeliveryDate');
UPDATE ad_column SET ad_column_id = 592945 WHERE ad_column_id = 581154 AND ad_table_id = 542626 AND columnname = 'DeliveryDate';

-- ad_column 581155 -> 592946 : ad_table_id = 542626 AND columnname = 'C_Country_ID'
UPDATE ad_accesslog SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_column_access SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_column_trl SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_columncallout SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_field SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_find SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_impformat_row SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_index_column SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_migrationdata SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_printformatitem SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_printlabelline SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_ref_table SET ad_display = 592946
  WHERE ad_display = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_ref_table SET ad_key = 592946
  WHERE ad_key = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_reportview_col SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592946
  WHERE link_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592946
  WHERE source_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_tab SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592946
  WHERE ad_columnsortorder_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592946
  WHERE ad_columnsortyesno_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_tab SET parent_column_id = 592946
  WHERE parent_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_wf_node SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_workbench SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE c_acctschema_element SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592946
  WHERE bpartnercolumn_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592946
  WHERE datedoccolumn_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE c_aggregationitem SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592946
  WHERE ad_column_olcand_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592946
  WHERE dlm_referencing_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592946
  WHERE parent_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE exp_formatline SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE i_elementvalue SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592946
  WHERE ad_column_id = 581155
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581155 AND p.ad_table_id = 542626 AND p.columnname = 'C_Country_ID');
UPDATE ad_column SET ad_column_id = 592946 WHERE ad_column_id = 581155 AND ad_table_id = 542626 AND columnname = 'C_Country_ID';

-- ad_column 581156 -> 592947 : ad_table_id = 542626 AND columnname = 'CountryName'
UPDATE ad_accesslog SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_column_access SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_column_trl SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_columncallout SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_field SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_field_contextmenu SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_find SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_impformat_row SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_index_column SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_migrationdata SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_printformatitem SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_printlabelline SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_ref_table SET ad_display = 592947
  WHERE ad_display = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_ref_table SET ad_key = 592947
  WHERE ad_key = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_reportview_col SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_searchdefinition SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592947
  WHERE link_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592947
  WHERE source_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_tab SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_tab SET ad_columnsortorder_id = 592947
  WHERE ad_columnsortorder_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_tab SET ad_columnsortyesno_id = 592947
  WHERE ad_columnsortyesno_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_tab SET parent_column_id = 592947
  WHERE parent_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_triggerui_action SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_triggerui_criteria SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_wf_nextcondition SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_wf_node SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_workbench SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE c_acctschema_element SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592947
  WHERE bpartnercolumn_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592947
  WHERE datedoccolumn_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE c_aggregationitem SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592947
  WHERE ad_column_olcand_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE datev_exportformatcolumn SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592947
  WHERE dlm_referencing_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592947
  WHERE parent_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE exp_formatline SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE i_elementvalue SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE webui_board_cardfield SET ad_column_id = 592947
  WHERE ad_column_id = 581156
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581156 AND p.ad_table_id = 542626 AND p.columnname = 'CountryName');
UPDATE ad_column SET ad_column_id = 592947 WHERE ad_column_id = 581156 AND ad_table_id = 542626 AND columnname = 'CountryName';

-- ad_column 581157 -> 592948 : ad_table_id = 542626 AND columnname = 'QtyWaiting'
UPDATE ad_accesslog SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_column_access SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_column_trl SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_columncallout SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_field SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_field_contextmenu SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_find SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_impformat_row SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_index_column SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_migrationdata SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_printformatitem SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_printlabelline SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_ref_table SET ad_display = 592948
  WHERE ad_display = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_ref_table SET ad_key = 592948
  WHERE ad_key = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_reportview_col SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_searchdefinition SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592948
  WHERE link_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592948
  WHERE source_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_tab SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_tab SET ad_columnsortorder_id = 592948
  WHERE ad_columnsortorder_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_tab SET ad_columnsortyesno_id = 592948
  WHERE ad_columnsortyesno_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_tab SET parent_column_id = 592948
  WHERE parent_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_triggerui_action SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_triggerui_criteria SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_wf_nextcondition SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_wf_node SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_workbench SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE c_acctschema_element SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592948
  WHERE bpartnercolumn_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592948
  WHERE datedoccolumn_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE c_aggregationitem SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592948
  WHERE ad_column_olcand_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE datev_exportformatcolumn SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592948
  WHERE dlm_referencing_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592948
  WHERE parent_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE exp_formatline SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE i_elementvalue SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE webui_board_cardfield SET ad_column_id = 592948
  WHERE ad_column_id = 581157
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581157 AND p.ad_table_id = 542626 AND p.columnname = 'QtyWaiting');
UPDATE ad_column SET ad_column_id = 592948 WHERE ad_column_id = 581157 AND ad_table_id = 542626 AND columnname = 'QtyWaiting';

-- ad_column 581158 -> 592949 : ad_table_id = 542626 AND columnname = 'QtyPicking'
UPDATE ad_accesslog SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_column_access SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_column_trl SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_columncallout SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_field SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_field_contextmenu SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_find SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_impformat_row SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_index_column SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_migrationdata SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_printformatitem SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_printlabelline SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_ref_table SET ad_display = 592949
  WHERE ad_display = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_ref_table SET ad_key = 592949
  WHERE ad_key = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_reportview_col SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_searchdefinition SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592949
  WHERE link_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592949
  WHERE source_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_tab SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_tab SET ad_columnsortorder_id = 592949
  WHERE ad_columnsortorder_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_tab SET ad_columnsortyesno_id = 592949
  WHERE ad_columnsortyesno_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_tab SET parent_column_id = 592949
  WHERE parent_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_triggerui_action SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_triggerui_criteria SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_wf_nextcondition SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_wf_node SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_workbench SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE c_acctschema_element SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592949
  WHERE bpartnercolumn_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592949
  WHERE datedoccolumn_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE c_aggregationitem SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592949
  WHERE ad_column_olcand_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE datev_exportformatcolumn SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592949
  WHERE dlm_referencing_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592949
  WHERE parent_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE exp_formatline SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE i_elementvalue SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE webui_board_cardfield SET ad_column_id = 592949
  WHERE ad_column_id = 581158
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581158 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPicking');
UPDATE ad_column SET ad_column_id = 592949 WHERE ad_column_id = 581158 AND ad_table_id = 542626 AND columnname = 'QtyPicking';

-- ad_column 581159 -> 592950 : ad_table_id = 542626 AND columnname = 'QtyPacking'
UPDATE ad_accesslog SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_column_access SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_column_trl SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_columncallout SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_field SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_field_contextmenu SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_find SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_impformat_row SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_index_column SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_migrationdata SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_printformatitem SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_printlabelline SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_ref_table SET ad_display = 592950
  WHERE ad_display = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_ref_table SET ad_key = 592950
  WHERE ad_key = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_reportview_col SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_searchdefinition SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592950
  WHERE link_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592950
  WHERE source_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_tab SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_tab SET ad_columnsortorder_id = 592950
  WHERE ad_columnsortorder_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_tab SET ad_columnsortyesno_id = 592950
  WHERE ad_columnsortyesno_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_tab SET parent_column_id = 592950
  WHERE parent_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_triggerui_action SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_triggerui_criteria SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_wf_nextcondition SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_wf_node SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_workbench SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE c_acctschema_element SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592950
  WHERE bpartnercolumn_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592950
  WHERE datedoccolumn_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE c_aggregationitem SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592950
  WHERE ad_column_olcand_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE datev_exportformatcolumn SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592950
  WHERE dlm_referencing_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592950
  WHERE parent_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE exp_formatline SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE i_elementvalue SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE webui_board_cardfield SET ad_column_id = 592950
  WHERE ad_column_id = 581159
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581159 AND p.ad_table_id = 542626 AND p.columnname = 'QtyPacking');
UPDATE ad_column SET ad_column_id = 592950 WHERE ad_column_id = 581159 AND ad_table_id = 542626 AND columnname = 'QtyPacking';

-- ad_column 581160 -> 592951 : ad_table_id = 542626 AND columnname = 'QtyTotal'
UPDATE ad_accesslog SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_column_access SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_column_trl SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_columncallout SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_field SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_field_contextmenu SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_find SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_impformat_row SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_index_column SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_migrationdata SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_printformatitem SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_printlabelline SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_ref_table SET ad_display = 592951
  WHERE ad_display = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_ref_table SET ad_key = 592951
  WHERE ad_key = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_reportview_col SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_searchdefinition SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592951
  WHERE link_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592951
  WHERE source_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_tab SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_tab SET ad_columnsortorder_id = 592951
  WHERE ad_columnsortorder_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_tab SET ad_columnsortyesno_id = 592951
  WHERE ad_columnsortyesno_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_tab SET parent_column_id = 592951
  WHERE parent_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_triggerui_action SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_triggerui_criteria SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_wf_nextcondition SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_wf_node SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_workbench SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE c_acctschema_element SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592951
  WHERE bpartnercolumn_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592951
  WHERE datedoccolumn_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE c_aggregationitem SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592951
  WHERE ad_column_olcand_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE datev_exportformatcolumn SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592951
  WHERE dlm_referencing_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592951
  WHERE parent_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE exp_formatline SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE i_elementvalue SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE webui_board_cardfield SET ad_column_id = 592951
  WHERE ad_column_id = 581160
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581160 AND p.ad_table_id = 542626 AND p.columnname = 'QtyTotal');
UPDATE ad_column SET ad_column_id = 592951 WHERE ad_column_id = 581160 AND ad_table_id = 542626 AND columnname = 'QtyTotal';

-- ad_column 581161 -> 592952 : ad_table_id = 542626 AND columnname = 'OrderLineCount'
UPDATE ad_accesslog SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_column_access SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_column_trl SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_columncallout SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_field SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_field_contextmenu SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_find SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_impformat_row SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_index_column SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_migrationdata SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_printformatitem SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_printlabelline SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_ref_table SET ad_display = 592952
  WHERE ad_display = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_ref_table SET ad_key = 592952
  WHERE ad_key = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_reportview_col SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_searchdefinition SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592952
  WHERE link_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592952
  WHERE source_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_tab SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_tab SET ad_columnsortorder_id = 592952
  WHERE ad_columnsortorder_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_tab SET ad_columnsortyesno_id = 592952
  WHERE ad_columnsortyesno_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_tab SET parent_column_id = 592952
  WHERE parent_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_triggerui_action SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_triggerui_criteria SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_wf_nextcondition SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_wf_node SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_workbench SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE c_acctschema_element SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592952
  WHERE bpartnercolumn_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592952
  WHERE datedoccolumn_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE c_aggregationitem SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592952
  WHERE ad_column_olcand_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE datev_exportformatcolumn SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592952
  WHERE dlm_referencing_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592952
  WHERE parent_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE exp_formatline SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE i_elementvalue SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE webui_board_cardfield SET ad_column_id = 592952
  WHERE ad_column_id = 581161
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581161 AND p.ad_table_id = 542626 AND p.columnname = 'OrderLineCount');
UPDATE ad_column SET ad_column_id = 592952 WHERE ad_column_id = 581161 AND ad_table_id = 542626 AND columnname = 'OrderLineCount';

-- ad_column 581162 -> 592953 : ad_table_id = 542626 AND columnname = 'AD_Client_ID'
UPDATE ad_accesslog SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_column_access SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_column_trl SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_columncallout SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_field SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_find SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_impformat_row SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_index_column SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_migrationdata SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_printformatitem SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_printlabelline SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_ref_table SET ad_display = 592953
  WHERE ad_display = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_ref_table SET ad_key = 592953
  WHERE ad_key = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_reportview_col SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592953
  WHERE link_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592953
  WHERE source_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_tab SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592953
  WHERE ad_columnsortorder_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592953
  WHERE ad_columnsortyesno_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_tab SET parent_column_id = 592953
  WHERE parent_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_wf_node SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_workbench SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE c_acctschema_element SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592953
  WHERE bpartnercolumn_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592953
  WHERE datedoccolumn_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE c_aggregationitem SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592953
  WHERE ad_column_olcand_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592953
  WHERE dlm_referencing_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592953
  WHERE parent_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE exp_formatline SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE i_elementvalue SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592953
  WHERE ad_column_id = 581162
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581162 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Client_ID');
UPDATE ad_column SET ad_column_id = 592953 WHERE ad_column_id = 581162 AND ad_table_id = 542626 AND columnname = 'AD_Client_ID';

-- ad_column 581163 -> 592954 : ad_table_id = 542626 AND columnname = 'AD_Org_ID'
UPDATE ad_accesslog SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_column_access SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_column_trl SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_columncallout SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_field SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_find SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_impformat_row SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_index_column SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_migrationdata SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_printformatitem SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_printlabelline SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_ref_table SET ad_display = 592954
  WHERE ad_display = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_ref_table SET ad_key = 592954
  WHERE ad_key = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_reportview_col SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592954
  WHERE link_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592954
  WHERE source_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_tab SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592954
  WHERE ad_columnsortorder_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592954
  WHERE ad_columnsortyesno_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_tab SET parent_column_id = 592954
  WHERE parent_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_wf_node SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_workbench SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE c_acctschema_element SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592954
  WHERE bpartnercolumn_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592954
  WHERE datedoccolumn_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE c_aggregationitem SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592954
  WHERE ad_column_olcand_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592954
  WHERE dlm_referencing_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592954
  WHERE parent_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE exp_formatline SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE i_elementvalue SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592954
  WHERE ad_column_id = 581163
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581163 AND p.ad_table_id = 542626 AND p.columnname = 'AD_Org_ID');
UPDATE ad_column SET ad_column_id = 592954 WHERE ad_column_id = 581163 AND ad_table_id = 542626 AND columnname = 'AD_Org_ID';

-- ad_column 581164 -> 592955 : ad_table_id = 542626 AND columnname = 'Updated'
UPDATE ad_accesslog SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_column_access SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_column_trl SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_columncallout SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_field SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_field_contextmenu SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_find SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_impformat_row SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_index_column SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_migrationdata SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_printformatitem SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_printlabelline SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_ref_table SET ad_display = 592955
  WHERE ad_display = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_ref_table SET ad_key = 592955
  WHERE ad_key = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_reportview_col SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_searchdefinition SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592955
  WHERE link_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592955
  WHERE source_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_tab SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_tab SET ad_columnsortorder_id = 592955
  WHERE ad_columnsortorder_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_tab SET ad_columnsortyesno_id = 592955
  WHERE ad_columnsortyesno_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_tab SET parent_column_id = 592955
  WHERE parent_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_triggerui_action SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_triggerui_criteria SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_wf_nextcondition SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_wf_node SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_workbench SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE c_acctschema_element SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592955
  WHERE bpartnercolumn_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592955
  WHERE datedoccolumn_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE c_aggregationitem SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592955
  WHERE ad_column_olcand_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE datev_exportformatcolumn SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592955
  WHERE dlm_referencing_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592955
  WHERE parent_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE exp_formatline SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE i_elementvalue SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE webui_board_cardfield SET ad_column_id = 592955
  WHERE ad_column_id = 581164
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581164 AND p.ad_table_id = 542626 AND p.columnname = 'Updated');
UPDATE ad_column SET ad_column_id = 592955 WHERE ad_column_id = 581164 AND ad_table_id = 542626 AND columnname = 'Updated';

-- ad_column 581165 -> 592956 : ad_table_id = 542626 AND columnname = 'UpdatedBy'
UPDATE ad_accesslog SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_column_access SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_column_trl SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_columncallout SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_field SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_field_contextmenu SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_find SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_impformat_row SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_index_column SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_migrationdata SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_printformatitem SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_printlabelline SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_ref_table SET ad_display = 592956
  WHERE ad_display = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_ref_table SET ad_key = 592956
  WHERE ad_key = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_reportview_col SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_searchdefinition SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592956
  WHERE link_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592956
  WHERE source_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_tab SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_tab SET ad_columnsortorder_id = 592956
  WHERE ad_columnsortorder_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_tab SET ad_columnsortyesno_id = 592956
  WHERE ad_columnsortyesno_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_tab SET parent_column_id = 592956
  WHERE parent_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_triggerui_action SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_triggerui_criteria SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_wf_nextcondition SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_wf_node SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_workbench SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE c_acctschema_element SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592956
  WHERE bpartnercolumn_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592956
  WHERE datedoccolumn_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE c_aggregationitem SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592956
  WHERE ad_column_olcand_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE datev_exportformatcolumn SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592956
  WHERE dlm_referencing_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592956
  WHERE parent_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE exp_formatline SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE i_elementvalue SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE webui_board_cardfield SET ad_column_id = 592956
  WHERE ad_column_id = 581165
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581165 AND p.ad_table_id = 542626 AND p.columnname = 'UpdatedBy');
UPDATE ad_column SET ad_column_id = 592956 WHERE ad_column_id = 581165 AND ad_table_id = 542626 AND columnname = 'UpdatedBy';

-- ad_column 581166 -> 592957 : ad_table_id = 542626 AND columnname = 'Created'
UPDATE ad_accesslog SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_column_access SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_column_trl SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_columncallout SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_field SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_field_contextmenu SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_find SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_impformat_row SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_index_column SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_migrationdata SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_printformatitem SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_printlabelline SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_ref_table SET ad_display = 592957
  WHERE ad_display = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_ref_table SET ad_key = 592957
  WHERE ad_key = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_reportview_col SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_searchdefinition SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592957
  WHERE link_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592957
  WHERE source_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_tab SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_tab SET ad_columnsortorder_id = 592957
  WHERE ad_columnsortorder_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_tab SET ad_columnsortyesno_id = 592957
  WHERE ad_columnsortyesno_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_tab SET parent_column_id = 592957
  WHERE parent_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_triggerui_action SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_triggerui_criteria SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_wf_nextcondition SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_wf_node SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_workbench SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE c_acctschema_element SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592957
  WHERE bpartnercolumn_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592957
  WHERE datedoccolumn_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE c_aggregationitem SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592957
  WHERE ad_column_olcand_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE datev_exportformatcolumn SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592957
  WHERE dlm_referencing_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592957
  WHERE parent_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE exp_formatline SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE i_elementvalue SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE webui_board_cardfield SET ad_column_id = 592957
  WHERE ad_column_id = 581166
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581166 AND p.ad_table_id = 542626 AND p.columnname = 'Created');
UPDATE ad_column SET ad_column_id = 592957 WHERE ad_column_id = 581166 AND ad_table_id = 542626 AND columnname = 'Created';

-- ad_column 581167 -> 592958 : ad_table_id = 542626 AND columnname = 'CreatedBy'
UPDATE ad_accesslog SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_column_access SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_column_trl SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_columncallout SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_field SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_field_contextmenu SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_find SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_impformat_row SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_index_column SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_migrationdata SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_printformatitem SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_printlabelline SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_ref_table SET ad_display = 592958
  WHERE ad_display = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_ref_table SET ad_key = 592958
  WHERE ad_key = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_reportview_col SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_searchdefinition SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592958
  WHERE link_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592958
  WHERE source_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_tab SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_tab SET ad_columnsortorder_id = 592958
  WHERE ad_columnsortorder_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_tab SET ad_columnsortyesno_id = 592958
  WHERE ad_columnsortyesno_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_tab SET parent_column_id = 592958
  WHERE parent_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_triggerui_action SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_triggerui_criteria SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_wf_nextcondition SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_wf_node SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_workbench SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE c_acctschema_element SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592958
  WHERE bpartnercolumn_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592958
  WHERE datedoccolumn_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE c_aggregationitem SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592958
  WHERE ad_column_olcand_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE datev_exportformatcolumn SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592958
  WHERE dlm_referencing_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592958
  WHERE parent_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE exp_formatline SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE i_elementvalue SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE webui_board_cardfield SET ad_column_id = 592958
  WHERE ad_column_id = 581167
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581167 AND p.ad_table_id = 542626 AND p.columnname = 'CreatedBy');
UPDATE ad_column SET ad_column_id = 592958 WHERE ad_column_id = 581167 AND ad_table_id = 542626 AND columnname = 'CreatedBy';

-- ad_column 581168 -> 592959 : ad_table_id = 542626 AND columnname = 'IsActive'
UPDATE ad_accesslog SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_column_access SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_column_trl SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_columncallout SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_field SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_field_contextmenu SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_find SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_impformat_row SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_index_column SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_migrationdata SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_printformatitem SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_printlabelline SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_ref_table SET ad_display = 592959
  WHERE ad_display = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_ref_table SET ad_key = 592959
  WHERE ad_key = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_reportview_col SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_searchdefinition SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592959
  WHERE link_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592959
  WHERE source_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_tab SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_tab SET ad_columnsortorder_id = 592959
  WHERE ad_columnsortorder_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_tab SET ad_columnsortyesno_id = 592959
  WHERE ad_columnsortyesno_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_tab SET parent_column_id = 592959
  WHERE parent_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_triggerui_action SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_triggerui_criteria SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_wf_nextcondition SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_wf_node SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_workbench SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE c_acctschema_element SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592959
  WHERE bpartnercolumn_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592959
  WHERE datedoccolumn_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE c_aggregationitem SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592959
  WHERE ad_column_olcand_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE datev_exportformatcolumn SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592959
  WHERE dlm_referencing_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592959
  WHERE parent_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE exp_formatline SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE i_elementvalue SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE webui_board_cardfield SET ad_column_id = 592959
  WHERE ad_column_id = 581168
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581168 AND p.ad_table_id = 542626 AND p.columnname = 'IsActive');
UPDATE ad_column SET ad_column_id = 592959 WHERE ad_column_id = 581168 AND ad_table_id = 542626 AND columnname = 'IsActive';

-- ad_column 581169 -> 592960 : ad_table_id = 542622 AND columnname = 'M_Picking_OrderBoard_Overview_v_ID'
UPDATE ad_accesslog SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_column_access SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_column_trl SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_columncallout SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_field SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_field_contextmenu SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_find SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_impformat_row SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_index_column SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_migrationdata SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_printformatitem SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_printlabelline SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_ref_table SET ad_display = 592960
  WHERE ad_display = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_ref_table SET ad_key = 592960
  WHERE ad_key = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_reportview_col SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_searchdefinition SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET link_column_id = 592960
  WHERE link_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_sqlcolumn_sourcetablecolumn SET source_column_id = 592960
  WHERE source_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_columnsortorder_id = 592960
  WHERE ad_columnsortorder_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET ad_columnsortyesno_id = 592960
  WHERE ad_columnsortyesno_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_tab SET parent_column_id = 592960
  WHERE parent_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_triggerui_action SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_triggerui_criteria SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_wf_nextcondition SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_wf_node SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_workbench SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_acctschema_element SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_advcommissionrelevantpo SET bpartnercolumn_id = 592960
  WHERE bpartnercolumn_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_advcommissionrelevantpo SET datedoccolumn_id = 592960
  WHERE datedoccolumn_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_aggregationitem SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE c_olcandaggandorder SET ad_column_olcand_id = 592960
  WHERE ad_column_olcand_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE datev_exportformatcolumn SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE dim_dimension_spec_assignment SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE dlm_partition_config_reference SET dlm_referencing_column_id = 592960
  WHERE dlm_referencing_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE es_fts_config_sourcemodel SET parent_column_id = 592960
  WHERE parent_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE es_fts_filter_joincolumn SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE exp_formatline SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE i_elementvalue SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE webui_board_cardfield SET ad_column_id = 592960
  WHERE ad_column_id = 581169
    AND EXISTS (SELECT 1 FROM ad_column p WHERE p.ad_column_id = 581169 AND p.ad_table_id = 542622 AND p.columnname = 'M_Picking_OrderBoard_Overview_v_ID');
UPDATE ad_column SET ad_column_id = 592960 WHERE ad_column_id = 581169 AND ad_table_id = 542622 AND columnname = 'M_Picking_OrderBoard_Overview_v_ID';

-- ad_tab 581037 -> 549335 : ad_window_id = 542168 AND ad_element_id = 585090
UPDATE ad_element_link SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_field SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_field SET included_tab_id = 549335
  WHERE included_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_tab SET included_tab_id = 549335
  WHERE included_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_tab SET template_tab_id = 549335
  WHERE template_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_tab_callout SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_tab_trl SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_table_process SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_triggerui SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_ui_element SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_ui_element SET inline_tab_id = 549335
  WHERE inline_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_ui_element SET labels_tab_id = 549335
  WHERE labels_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_ui_section SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_user_sortpref_hdr SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_userdef_tab SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_userquery SET ad_tab_id = 549335
  WHERE ad_tab_id = 581037
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581037 AND p.ad_window_id = 542168 AND p.ad_element_id = 585090);
UPDATE ad_tab SET ad_tab_id = 549335 WHERE ad_tab_id = 581037 AND ad_window_id = 542168 AND ad_element_id = 585090;

-- ad_tab 581038 -> 549336 : ad_window_id = 542168 AND ad_element_id = 585091
UPDATE ad_element_link SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_field SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_field SET included_tab_id = 549336
  WHERE included_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_tab SET included_tab_id = 549336
  WHERE included_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_tab SET template_tab_id = 549336
  WHERE template_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_tab_callout SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_tab_trl SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_table_process SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_triggerui SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_ui_element SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_ui_element SET inline_tab_id = 549336
  WHERE inline_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_ui_element SET labels_tab_id = 549336
  WHERE labels_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_ui_section SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_user_sortpref_hdr SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_userdef_tab SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_userquery SET ad_tab_id = 549336
  WHERE ad_tab_id = 581038
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581038 AND p.ad_window_id = 542168 AND p.ad_element_id = 585091);
UPDATE ad_tab SET ad_tab_id = 549336 WHERE ad_tab_id = 581038 AND ad_window_id = 542168 AND ad_element_id = 585091;

-- ad_tab 581039 -> 549337 : ad_window_id = 542168 AND ad_element_id = 585092
UPDATE ad_element_link SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_field SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_field SET included_tab_id = 549337
  WHERE included_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_tab SET included_tab_id = 549337
  WHERE included_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_tab SET template_tab_id = 549337
  WHERE template_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_tab_callout SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_tab_trl SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_table_process SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_triggerui SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_ui_element SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_ui_element SET inline_tab_id = 549337
  WHERE inline_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_ui_element SET labels_tab_id = 549337
  WHERE labels_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_ui_section SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_user_sortpref_hdr SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_userdef_tab SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_userquery SET ad_tab_id = 549337
  WHERE ad_tab_id = 581039
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581039 AND p.ad_window_id = 542168 AND p.ad_element_id = 585092);
UPDATE ad_tab SET ad_tab_id = 549337 WHERE ad_tab_id = 581039 AND ad_window_id = 542168 AND ad_element_id = 585092;

-- ad_tab 581171 -> 549338 : ad_window_id = 542168 AND ad_element_id = 585098
UPDATE ad_element_link SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_field SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_field SET included_tab_id = 549338
  WHERE included_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_tab SET included_tab_id = 549338
  WHERE included_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_tab SET template_tab_id = 549338
  WHERE template_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_tab_callout SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_tab_trl SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_table_process SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_triggerui SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_ui_element SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_ui_element SET inline_tab_id = 549338
  WHERE inline_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_ui_element SET labels_tab_id = 549338
  WHERE labels_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_ui_section SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_user_sortpref_hdr SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_userdef_tab SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_userquery SET ad_tab_id = 549338
  WHERE ad_tab_id = 581171
    AND EXISTS (SELECT 1 FROM ad_tab p WHERE p.ad_tab_id = 581171 AND p.ad_window_id = 542168 AND p.ad_element_id = 585098);
UPDATE ad_tab SET ad_tab_id = 549338 WHERE ad_tab_id = 581171 AND ad_window_id = 542168 AND ad_element_id = 585098;

-- ad_field 581044 -> 781382 : ad_tab_id = 549335 AND ad_column_id = 592898
UPDATE ad_element_link SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_field_contextmenu SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_field_trl SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_ui_element SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_ui_element SET labels_selector_field_id = 781382
  WHERE labels_selector_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_ui_elementfield SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_user_sortpref_line SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_userdef_field SET ad_field_id = 781382
  WHERE ad_field_id = 581044
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581044 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592898);
UPDATE ad_field SET ad_field_id = 781382 WHERE ad_field_id = 581044 AND ad_tab_id = 549335 AND ad_column_id = 592898;

-- ad_field 581045 -> 781383 : ad_tab_id = 549335 AND ad_column_id = 592899
UPDATE ad_element_link SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_field_contextmenu SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_field_trl SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_ui_element SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_ui_element SET labels_selector_field_id = 781383
  WHERE labels_selector_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_ui_elementfield SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_user_sortpref_line SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_userdef_field SET ad_field_id = 781383
  WHERE ad_field_id = 581045
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581045 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592899);
UPDATE ad_field SET ad_field_id = 781383 WHERE ad_field_id = 581045 AND ad_tab_id = 549335 AND ad_column_id = 592899;

-- ad_field 581046 -> 781384 : ad_tab_id = 549335 AND ad_column_id = 592900
UPDATE ad_element_link SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_field_contextmenu SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_field_trl SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_ui_element SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_ui_element SET labels_selector_field_id = 781384
  WHERE labels_selector_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_ui_elementfield SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_user_sortpref_line SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_userdef_field SET ad_field_id = 781384
  WHERE ad_field_id = 581046
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581046 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592900);
UPDATE ad_field SET ad_field_id = 781384 WHERE ad_field_id = 581046 AND ad_tab_id = 549335 AND ad_column_id = 592900;

-- ad_field 581047 -> 781385 : ad_tab_id = 549335 AND ad_column_id = 592902
UPDATE ad_element_link SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_field_contextmenu SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_field_trl SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_ui_element SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_ui_element SET labels_selector_field_id = 781385
  WHERE labels_selector_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_ui_elementfield SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_user_sortpref_line SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_userdef_field SET ad_field_id = 781385
  WHERE ad_field_id = 581047
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581047 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592902);
UPDATE ad_field SET ad_field_id = 781385 WHERE ad_field_id = 581047 AND ad_tab_id = 549335 AND ad_column_id = 592902;

-- ad_field 581048 -> 781386 : ad_tab_id = 549335 AND ad_column_id = 592904
UPDATE ad_element_link SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_field_contextmenu SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_field_trl SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_ui_element SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_ui_element SET labels_selector_field_id = 781386
  WHERE labels_selector_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_ui_elementfield SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_user_sortpref_line SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_userdef_field SET ad_field_id = 781386
  WHERE ad_field_id = 581048
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581048 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592904);
UPDATE ad_field SET ad_field_id = 781386 WHERE ad_field_id = 581048 AND ad_tab_id = 549335 AND ad_column_id = 592904;

-- ad_field 581049 -> 781387 : ad_tab_id = 549335 AND ad_column_id = 592903
UPDATE ad_element_link SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_field_contextmenu SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_field_trl SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_ui_element SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_ui_element SET labels_selector_field_id = 781387
  WHERE labels_selector_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_ui_elementfield SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_user_sortpref_line SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_userdef_field SET ad_field_id = 781387
  WHERE ad_field_id = 581049
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581049 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592903);
UPDATE ad_field SET ad_field_id = 781387 WHERE ad_field_id = 581049 AND ad_tab_id = 549335 AND ad_column_id = 592903;

-- ad_field 581050 -> 781388 : ad_tab_id = 549335 AND ad_column_id = 592905
UPDATE ad_element_link SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_field_contextmenu SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_field_trl SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_ui_element SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_ui_element SET labels_selector_field_id = 781388
  WHERE labels_selector_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_ui_elementfield SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_user_sortpref_line SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_userdef_field SET ad_field_id = 781388
  WHERE ad_field_id = 581050
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581050 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592905);
UPDATE ad_field SET ad_field_id = 781388 WHERE ad_field_id = 581050 AND ad_tab_id = 549335 AND ad_column_id = 592905;

-- ad_field 581095 -> 781389 : ad_tab_id = 549335 AND ad_column_id = 592906
UPDATE ad_element_link SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_field_contextmenu SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_field_trl SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_ui_element SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_ui_element SET labels_selector_field_id = 781389
  WHERE labels_selector_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_ui_elementfield SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_user_sortpref_line SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_userdef_field SET ad_field_id = 781389
  WHERE ad_field_id = 581095
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581095 AND p.ad_tab_id = 549335 AND p.ad_column_id = 592906);
UPDATE ad_field SET ad_field_id = 781389 WHERE ad_field_id = 581095 AND ad_tab_id = 549335 AND ad_column_id = 592906;

-- ad_field 581051 -> 781390 : ad_tab_id = 549336 AND ad_column_id = 592898
UPDATE ad_element_link SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_field_contextmenu SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_field_trl SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_ui_element SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_ui_element SET labels_selector_field_id = 781390
  WHERE labels_selector_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_ui_elementfield SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_user_sortpref_line SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_userdef_field SET ad_field_id = 781390
  WHERE ad_field_id = 581051
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581051 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592898);
UPDATE ad_field SET ad_field_id = 781390 WHERE ad_field_id = 581051 AND ad_tab_id = 549336 AND ad_column_id = 592898;

-- ad_field 581052 -> 781391 : ad_tab_id = 549336 AND ad_column_id = 592899
UPDATE ad_element_link SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_field_contextmenu SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_field_trl SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_ui_element SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_ui_element SET labels_selector_field_id = 781391
  WHERE labels_selector_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_ui_elementfield SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_user_sortpref_line SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_userdef_field SET ad_field_id = 781391
  WHERE ad_field_id = 581052
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581052 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592899);
UPDATE ad_field SET ad_field_id = 781391 WHERE ad_field_id = 581052 AND ad_tab_id = 549336 AND ad_column_id = 592899;

-- ad_field 581053 -> 781392 : ad_tab_id = 549336 AND ad_column_id = 592900
UPDATE ad_element_link SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_field_contextmenu SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_field_trl SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_ui_element SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_ui_element SET labels_selector_field_id = 781392
  WHERE labels_selector_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_ui_elementfield SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_user_sortpref_line SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_userdef_field SET ad_field_id = 781392
  WHERE ad_field_id = 581053
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581053 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592900);
UPDATE ad_field SET ad_field_id = 781392 WHERE ad_field_id = 581053 AND ad_tab_id = 549336 AND ad_column_id = 592900;

-- ad_field 581054 -> 781393 : ad_tab_id = 549336 AND ad_column_id = 592902
UPDATE ad_element_link SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_field_contextmenu SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_field_trl SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_ui_element SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_ui_element SET labels_selector_field_id = 781393
  WHERE labels_selector_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_ui_elementfield SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_user_sortpref_line SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_userdef_field SET ad_field_id = 781393
  WHERE ad_field_id = 581054
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581054 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592902);
UPDATE ad_field SET ad_field_id = 781393 WHERE ad_field_id = 581054 AND ad_tab_id = 549336 AND ad_column_id = 592902;

-- ad_field 581055 -> 781394 : ad_tab_id = 549336 AND ad_column_id = 592904
UPDATE ad_element_link SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_field_contextmenu SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_field_trl SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_ui_element SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_ui_element SET labels_selector_field_id = 781394
  WHERE labels_selector_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_ui_elementfield SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_user_sortpref_line SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_userdef_field SET ad_field_id = 781394
  WHERE ad_field_id = 581055
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581055 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592904);
UPDATE ad_field SET ad_field_id = 781394 WHERE ad_field_id = 581055 AND ad_tab_id = 549336 AND ad_column_id = 592904;

-- ad_field 581056 -> 781395 : ad_tab_id = 549336 AND ad_column_id = 592903
UPDATE ad_element_link SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_field_contextmenu SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_field_trl SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_ui_element SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_ui_element SET labels_selector_field_id = 781395
  WHERE labels_selector_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_ui_elementfield SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_user_sortpref_line SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_userdef_field SET ad_field_id = 781395
  WHERE ad_field_id = 581056
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581056 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592903);
UPDATE ad_field SET ad_field_id = 781395 WHERE ad_field_id = 581056 AND ad_tab_id = 549336 AND ad_column_id = 592903;

-- ad_field 581057 -> 781396 : ad_tab_id = 549336 AND ad_column_id = 592905
UPDATE ad_element_link SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_field_contextmenu SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_field_trl SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_ui_element SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_ui_element SET labels_selector_field_id = 781396
  WHERE labels_selector_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_ui_elementfield SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_user_sortpref_line SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_userdef_field SET ad_field_id = 781396
  WHERE ad_field_id = 581057
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581057 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592905);
UPDATE ad_field SET ad_field_id = 781396 WHERE ad_field_id = 581057 AND ad_tab_id = 549336 AND ad_column_id = 592905;

-- ad_field 581096 -> 781397 : ad_tab_id = 549336 AND ad_column_id = 592906
UPDATE ad_element_link SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_field_contextmenu SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_field_trl SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_ui_element SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_ui_element SET labels_selector_field_id = 781397
  WHERE labels_selector_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_ui_elementfield SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_user_sortpref_line SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_userdef_field SET ad_field_id = 781397
  WHERE ad_field_id = 581096
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581096 AND p.ad_tab_id = 549336 AND p.ad_column_id = 592906);
UPDATE ad_field SET ad_field_id = 781397 WHERE ad_field_id = 581096 AND ad_tab_id = 549336 AND ad_column_id = 592906;

-- ad_field 581058 -> 781398 : ad_tab_id = 549337 AND ad_column_id = 592898
UPDATE ad_element_link SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_field_contextmenu SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_field_trl SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_ui_element SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_ui_element SET labels_selector_field_id = 781398
  WHERE labels_selector_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_ui_elementfield SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_user_sortpref_line SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_userdef_field SET ad_field_id = 781398
  WHERE ad_field_id = 581058
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581058 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592898);
UPDATE ad_field SET ad_field_id = 781398 WHERE ad_field_id = 581058 AND ad_tab_id = 549337 AND ad_column_id = 592898;

-- ad_field 581059 -> 781399 : ad_tab_id = 549337 AND ad_column_id = 592899
UPDATE ad_element_link SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_field_contextmenu SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_field_trl SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_ui_element SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_ui_element SET labels_selector_field_id = 781399
  WHERE labels_selector_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_ui_elementfield SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_user_sortpref_line SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_userdef_field SET ad_field_id = 781399
  WHERE ad_field_id = 581059
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581059 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592899);
UPDATE ad_field SET ad_field_id = 781399 WHERE ad_field_id = 581059 AND ad_tab_id = 549337 AND ad_column_id = 592899;

-- ad_field 581060 -> 781400 : ad_tab_id = 549337 AND ad_column_id = 592900
UPDATE ad_element_link SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_field_contextmenu SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_field_trl SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_ui_element SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_ui_element SET labels_selector_field_id = 781400
  WHERE labels_selector_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_ui_elementfield SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_user_sortpref_line SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_userdef_field SET ad_field_id = 781400
  WHERE ad_field_id = 581060
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581060 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592900);
UPDATE ad_field SET ad_field_id = 781400 WHERE ad_field_id = 581060 AND ad_tab_id = 549337 AND ad_column_id = 592900;

-- ad_field 581061 -> 781401 : ad_tab_id = 549337 AND ad_column_id = 592902
UPDATE ad_element_link SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_field_contextmenu SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_field_trl SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_ui_element SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_ui_element SET labels_selector_field_id = 781401
  WHERE labels_selector_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_ui_elementfield SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_user_sortpref_line SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_userdef_field SET ad_field_id = 781401
  WHERE ad_field_id = 581061
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581061 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592902);
UPDATE ad_field SET ad_field_id = 781401 WHERE ad_field_id = 581061 AND ad_tab_id = 549337 AND ad_column_id = 592902;

-- ad_field 581062 -> 781402 : ad_tab_id = 549337 AND ad_column_id = 592904
UPDATE ad_element_link SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_field_contextmenu SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_field_trl SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_ui_element SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_ui_element SET labels_selector_field_id = 781402
  WHERE labels_selector_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_ui_elementfield SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_user_sortpref_line SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_userdef_field SET ad_field_id = 781402
  WHERE ad_field_id = 581062
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581062 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592904);
UPDATE ad_field SET ad_field_id = 781402 WHERE ad_field_id = 581062 AND ad_tab_id = 549337 AND ad_column_id = 592904;

-- ad_field 581063 -> 781403 : ad_tab_id = 549337 AND ad_column_id = 592903
UPDATE ad_element_link SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_field_contextmenu SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_field_trl SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_ui_element SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_ui_element SET labels_selector_field_id = 781403
  WHERE labels_selector_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_ui_elementfield SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_user_sortpref_line SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_userdef_field SET ad_field_id = 781403
  WHERE ad_field_id = 581063
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581063 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592903);
UPDATE ad_field SET ad_field_id = 781403 WHERE ad_field_id = 581063 AND ad_tab_id = 549337 AND ad_column_id = 592903;

-- ad_field 581064 -> 781404 : ad_tab_id = 549337 AND ad_column_id = 592905
UPDATE ad_element_link SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_field_contextmenu SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_field_trl SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_ui_element SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_ui_element SET labels_selector_field_id = 781404
  WHERE labels_selector_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_ui_elementfield SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_user_sortpref_line SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_userdef_field SET ad_field_id = 781404
  WHERE ad_field_id = 581064
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581064 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592905);
UPDATE ad_field SET ad_field_id = 781404 WHERE ad_field_id = 581064 AND ad_tab_id = 549337 AND ad_column_id = 592905;

-- ad_field 581097 -> 781405 : ad_tab_id = 549337 AND ad_column_id = 592906
UPDATE ad_element_link SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_field_contextmenu SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_field_trl SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_ui_element SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_ui_element SET labels_selector_field_id = 781405
  WHERE labels_selector_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_ui_elementfield SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_user_sortpref_line SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_userdef_field SET ad_field_id = 781405
  WHERE ad_field_id = 581097
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581097 AND p.ad_tab_id = 549337 AND p.ad_column_id = 592906);
UPDATE ad_field SET ad_field_id = 781405 WHERE ad_field_id = 581097 AND ad_tab_id = 549337 AND ad_column_id = 592906;

-- ad_field 581172 -> 781407 : ad_tab_id = 549338 AND ad_column_id = 592942
UPDATE ad_element_link SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_field_contextmenu SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_field_trl SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_ui_element SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_ui_element SET labels_selector_field_id = 781407
  WHERE labels_selector_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_ui_elementfield SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_user_sortpref_line SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_userdef_field SET ad_field_id = 781407
  WHERE ad_field_id = 581172
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581172 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592942);
UPDATE ad_field SET ad_field_id = 781407 WHERE ad_field_id = 581172 AND ad_tab_id = 549338 AND ad_column_id = 592942;

-- ad_field 581173 -> 781408 : ad_tab_id = 549338 AND ad_column_id = 592943
UPDATE ad_element_link SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_field_contextmenu SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_field_trl SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_ui_element SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_ui_element SET labels_selector_field_id = 781408
  WHERE labels_selector_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_ui_elementfield SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_user_sortpref_line SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_userdef_field SET ad_field_id = 781408
  WHERE ad_field_id = 581173
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581173 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592943);
UPDATE ad_field SET ad_field_id = 781408 WHERE ad_field_id = 581173 AND ad_tab_id = 549338 AND ad_column_id = 592943;

-- ad_field 581174 -> 781409 : ad_tab_id = 549338 AND ad_column_id = 592944
UPDATE ad_element_link SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_field_contextmenu SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_field_trl SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_ui_element SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_ui_element SET labels_selector_field_id = 781409
  WHERE labels_selector_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_ui_elementfield SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_user_sortpref_line SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_userdef_field SET ad_field_id = 781409
  WHERE ad_field_id = 581174
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581174 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592944);
UPDATE ad_field SET ad_field_id = 781409 WHERE ad_field_id = 581174 AND ad_tab_id = 549338 AND ad_column_id = 592944;

-- ad_field 581175 -> 781410 : ad_tab_id = 549338 AND ad_column_id = 592945
UPDATE ad_element_link SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_field_contextmenu SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_field_trl SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_ui_element SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_ui_element SET labels_selector_field_id = 781410
  WHERE labels_selector_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_ui_elementfield SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_user_sortpref_line SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_userdef_field SET ad_field_id = 781410
  WHERE ad_field_id = 581175
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581175 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592945);
UPDATE ad_field SET ad_field_id = 781410 WHERE ad_field_id = 581175 AND ad_tab_id = 549338 AND ad_column_id = 592945;

-- ad_field 581176 -> 781411 : ad_tab_id = 549338 AND ad_column_id = 592947
UPDATE ad_element_link SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_field_contextmenu SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_field_trl SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_ui_element SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_ui_element SET labels_selector_field_id = 781411
  WHERE labels_selector_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_ui_elementfield SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_user_sortpref_line SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_userdef_field SET ad_field_id = 781411
  WHERE ad_field_id = 581176
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581176 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592947);
UPDATE ad_field SET ad_field_id = 781411 WHERE ad_field_id = 581176 AND ad_tab_id = 549338 AND ad_column_id = 592947;

-- ad_field 581177 -> 781412 : ad_tab_id = 549338 AND ad_column_id = 592946
UPDATE ad_element_link SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_field_contextmenu SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_field_trl SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_ui_element SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_ui_element SET labels_selector_field_id = 781412
  WHERE labels_selector_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_ui_elementfield SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_user_sortpref_line SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_userdef_field SET ad_field_id = 781412
  WHERE ad_field_id = 581177
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581177 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592946);
UPDATE ad_field SET ad_field_id = 781412 WHERE ad_field_id = 581177 AND ad_tab_id = 549338 AND ad_column_id = 592946;

-- ad_field 581178 -> 781413 : ad_tab_id = 549338 AND ad_column_id = 592948
UPDATE ad_element_link SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_field_contextmenu SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_field_trl SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_ui_element SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_ui_element SET labels_selector_field_id = 781413
  WHERE labels_selector_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_ui_elementfield SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_user_sortpref_line SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_userdef_field SET ad_field_id = 781413
  WHERE ad_field_id = 581178
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581178 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592948);
UPDATE ad_field SET ad_field_id = 781413 WHERE ad_field_id = 581178 AND ad_tab_id = 549338 AND ad_column_id = 592948;

-- ad_field 581179 -> 781414 : ad_tab_id = 549338 AND ad_column_id = 592949
UPDATE ad_element_link SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_field_contextmenu SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_field_trl SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_ui_element SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_ui_element SET labels_selector_field_id = 781414
  WHERE labels_selector_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_ui_elementfield SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_user_sortpref_line SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_userdef_field SET ad_field_id = 781414
  WHERE ad_field_id = 581179
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581179 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592949);
UPDATE ad_field SET ad_field_id = 781414 WHERE ad_field_id = 581179 AND ad_tab_id = 549338 AND ad_column_id = 592949;

-- ad_field 581180 -> 781415 : ad_tab_id = 549338 AND ad_column_id = 592950
UPDATE ad_element_link SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_field_contextmenu SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_field_trl SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_ui_element SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_ui_element SET labels_selector_field_id = 781415
  WHERE labels_selector_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_ui_elementfield SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_user_sortpref_line SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_userdef_field SET ad_field_id = 781415
  WHERE ad_field_id = 581180
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581180 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592950);
UPDATE ad_field SET ad_field_id = 781415 WHERE ad_field_id = 581180 AND ad_tab_id = 549338 AND ad_column_id = 592950;

-- ad_field 581181 -> 781416 : ad_tab_id = 549338 AND ad_column_id = 592951
UPDATE ad_element_link SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_field_contextmenu SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_field_trl SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_ui_element SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_ui_element SET labels_selector_field_id = 781416
  WHERE labels_selector_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_ui_elementfield SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_user_sortpref_line SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_userdef_field SET ad_field_id = 781416
  WHERE ad_field_id = 581181
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581181 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592951);
UPDATE ad_field SET ad_field_id = 781416 WHERE ad_field_id = 581181 AND ad_tab_id = 549338 AND ad_column_id = 592951;

-- ad_field 581182 -> 781417 : ad_tab_id = 549338 AND ad_column_id = 592952
UPDATE ad_element_link SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_field_contextmenu SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_field_trl SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_ui_element SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_ui_element SET labels_selector_field_id = 781417
  WHERE labels_selector_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_ui_elementfield SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_user_sortpref_line SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_userdef_field SET ad_field_id = 781417
  WHERE ad_field_id = 581182
    AND EXISTS (SELECT 1 FROM ad_field p WHERE p.ad_field_id = 581182 AND p.ad_tab_id = 549338 AND p.ad_column_id = 592952);
UPDATE ad_field SET ad_field_id = 781417 WHERE ad_field_id = 581182 AND ad_tab_id = 549338 AND ad_column_id = 592952;

-- ad_ui_section 581065 -> 547844 : ad_tab_id = 549335
UPDATE ad_ui_column SET ad_ui_section_id = 547844
  WHERE ad_ui_section_id = 581065
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581065 AND p.ad_tab_id = 549335);
UPDATE ad_ui_section_trl SET ad_ui_section_id = 547844
  WHERE ad_ui_section_id = 581065
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581065 AND p.ad_tab_id = 549335);
UPDATE ad_ui_section SET ad_ui_section_id = 547844 WHERE ad_ui_section_id = 581065 AND ad_tab_id = 549335;

-- ad_ui_section 581075 -> 547845 : ad_tab_id = 549336
UPDATE ad_ui_column SET ad_ui_section_id = 547845
  WHERE ad_ui_section_id = 581075
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581075 AND p.ad_tab_id = 549336);
UPDATE ad_ui_section_trl SET ad_ui_section_id = 547845
  WHERE ad_ui_section_id = 581075
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581075 AND p.ad_tab_id = 549336);
UPDATE ad_ui_section SET ad_ui_section_id = 547845 WHERE ad_ui_section_id = 581075 AND ad_tab_id = 549336;

-- ad_ui_section 581085 -> 547846 : ad_tab_id = 549337
UPDATE ad_ui_column SET ad_ui_section_id = 547846
  WHERE ad_ui_section_id = 581085
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581085 AND p.ad_tab_id = 549337);
UPDATE ad_ui_section_trl SET ad_ui_section_id = 547846
  WHERE ad_ui_section_id = 581085
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581085 AND p.ad_tab_id = 549337);
UPDATE ad_ui_section SET ad_ui_section_id = 547846 WHERE ad_ui_section_id = 581085 AND ad_tab_id = 549337;

-- ad_ui_section 581183 -> 547847 : ad_tab_id = 549338
UPDATE ad_ui_column SET ad_ui_section_id = 547847
  WHERE ad_ui_section_id = 581183
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581183 AND p.ad_tab_id = 549338);
UPDATE ad_ui_section_trl SET ad_ui_section_id = 547847
  WHERE ad_ui_section_id = 581183
    AND EXISTS (SELECT 1 FROM ad_ui_section p WHERE p.ad_ui_section_id = 581183 AND p.ad_tab_id = 549338);
UPDATE ad_ui_section SET ad_ui_section_id = 547847 WHERE ad_ui_section_id = 581183 AND ad_tab_id = 549338;

-- ad_ui_column 581066 -> 549585 : ad_ui_section_id = 547844
UPDATE ad_ui_elementgroup SET ad_ui_column_id = 549585
  WHERE ad_ui_column_id = 581066
    AND EXISTS (SELECT 1 FROM ad_ui_column p WHERE p.ad_ui_column_id = 581066 AND p.ad_ui_section_id = 547844);
UPDATE ad_ui_column SET ad_ui_column_id = 549585 WHERE ad_ui_column_id = 581066 AND ad_ui_section_id = 547844;

-- ad_ui_column 581076 -> 549586 : ad_ui_section_id = 547845
UPDATE ad_ui_elementgroup SET ad_ui_column_id = 549586
  WHERE ad_ui_column_id = 581076
    AND EXISTS (SELECT 1 FROM ad_ui_column p WHERE p.ad_ui_column_id = 581076 AND p.ad_ui_section_id = 547845);
UPDATE ad_ui_column SET ad_ui_column_id = 549586 WHERE ad_ui_column_id = 581076 AND ad_ui_section_id = 547845;

-- ad_ui_column 581086 -> 549587 : ad_ui_section_id = 547846
UPDATE ad_ui_elementgroup SET ad_ui_column_id = 549587
  WHERE ad_ui_column_id = 581086
    AND EXISTS (SELECT 1 FROM ad_ui_column p WHERE p.ad_ui_column_id = 581086 AND p.ad_ui_section_id = 547846);
UPDATE ad_ui_column SET ad_ui_column_id = 549587 WHERE ad_ui_column_id = 581086 AND ad_ui_section_id = 547846;

-- ad_ui_column 581184 -> 549588 : ad_ui_section_id = 547847
UPDATE ad_ui_elementgroup SET ad_ui_column_id = 549588
  WHERE ad_ui_column_id = 581184
    AND EXISTS (SELECT 1 FROM ad_ui_column p WHERE p.ad_ui_column_id = 581184 AND p.ad_ui_section_id = 547847);
UPDATE ad_ui_column SET ad_ui_column_id = 549588 WHERE ad_ui_column_id = 581184 AND ad_ui_section_id = 547847;

-- ad_ui_elementgroup 581067 -> 555489 : ad_ui_column_id = 549585
UPDATE ad_ui_element SET ad_ui_elementgroup_id = 555489
  WHERE ad_ui_elementgroup_id = 581067
    AND EXISTS (SELECT 1 FROM ad_ui_elementgroup p WHERE p.ad_ui_elementgroup_id = 581067 AND p.ad_ui_column_id = 549585);
UPDATE ad_ui_elementgroup SET ad_ui_elementgroup_id = 555489 WHERE ad_ui_elementgroup_id = 581067 AND ad_ui_column_id = 549585;

-- ad_ui_elementgroup 581077 -> 555490 : ad_ui_column_id = 549586
UPDATE ad_ui_element SET ad_ui_elementgroup_id = 555490
  WHERE ad_ui_elementgroup_id = 581077
    AND EXISTS (SELECT 1 FROM ad_ui_elementgroup p WHERE p.ad_ui_elementgroup_id = 581077 AND p.ad_ui_column_id = 549586);
UPDATE ad_ui_elementgroup SET ad_ui_elementgroup_id = 555490 WHERE ad_ui_elementgroup_id = 581077 AND ad_ui_column_id = 549586;

-- ad_ui_elementgroup 581087 -> 555491 : ad_ui_column_id = 549587
UPDATE ad_ui_element SET ad_ui_elementgroup_id = 555491
  WHERE ad_ui_elementgroup_id = 581087
    AND EXISTS (SELECT 1 FROM ad_ui_elementgroup p WHERE p.ad_ui_elementgroup_id = 581087 AND p.ad_ui_column_id = 549587);
UPDATE ad_ui_elementgroup SET ad_ui_elementgroup_id = 555491 WHERE ad_ui_elementgroup_id = 581087 AND ad_ui_column_id = 549587;

-- ad_ui_elementgroup 581185 -> 555492 : ad_ui_column_id = 549588
UPDATE ad_ui_element SET ad_ui_elementgroup_id = 555492
  WHERE ad_ui_elementgroup_id = 581185
    AND EXISTS (SELECT 1 FROM ad_ui_elementgroup p WHERE p.ad_ui_elementgroup_id = 581185 AND p.ad_ui_column_id = 549588);
UPDATE ad_ui_elementgroup SET ad_ui_elementgroup_id = 555492 WHERE ad_ui_elementgroup_id = 581185 AND ad_ui_column_id = 549588;

-- ad_ui_element 581068 -> 652498 : ad_tab_id = 549335 AND ad_field_id = 781382
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652498
  WHERE ad_ui_element_id = 581068
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581068 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781382);
UPDATE ad_ui_element SET ad_ui_element_id = 652498 WHERE ad_ui_element_id = 581068 AND ad_tab_id = 549335 AND ad_field_id = 781382;

-- ad_ui_element 581069 -> 652499 : ad_tab_id = 549335 AND ad_field_id = 781383
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652499
  WHERE ad_ui_element_id = 581069
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581069 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781383);
UPDATE ad_ui_element SET ad_ui_element_id = 652499 WHERE ad_ui_element_id = 581069 AND ad_tab_id = 549335 AND ad_field_id = 781383;

-- ad_ui_element 581070 -> 652500 : ad_tab_id = 549335 AND ad_field_id = 781384
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652500
  WHERE ad_ui_element_id = 581070
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581070 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781384);
UPDATE ad_ui_element SET ad_ui_element_id = 652500 WHERE ad_ui_element_id = 581070 AND ad_tab_id = 549335 AND ad_field_id = 781384;

-- ad_ui_element 581071 -> 652501 : ad_tab_id = 549335 AND ad_field_id = 781385
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652501
  WHERE ad_ui_element_id = 581071
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581071 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781385);
UPDATE ad_ui_element SET ad_ui_element_id = 652501 WHERE ad_ui_element_id = 581071 AND ad_tab_id = 549335 AND ad_field_id = 781385;

-- ad_ui_element 581072 -> 652502 : ad_tab_id = 549335 AND ad_field_id = 781386
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652502
  WHERE ad_ui_element_id = 581072
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581072 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781386);
UPDATE ad_ui_element SET ad_ui_element_id = 652502 WHERE ad_ui_element_id = 581072 AND ad_tab_id = 549335 AND ad_field_id = 781386;

-- ad_ui_element 581073 -> 652503 : ad_tab_id = 549335 AND ad_field_id = 781387
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652503
  WHERE ad_ui_element_id = 581073
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581073 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781387);
UPDATE ad_ui_element SET ad_ui_element_id = 652503 WHERE ad_ui_element_id = 581073 AND ad_tab_id = 549335 AND ad_field_id = 781387;

-- ad_ui_element 581074 -> 652504 : ad_tab_id = 549335 AND ad_field_id = 781388
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652504
  WHERE ad_ui_element_id = 581074
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581074 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781388);
UPDATE ad_ui_element SET ad_ui_element_id = 652504 WHERE ad_ui_element_id = 581074 AND ad_tab_id = 549335 AND ad_field_id = 781388;

-- ad_ui_element 581098 -> 652505 : ad_tab_id = 549335 AND ad_field_id = 781389
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652505
  WHERE ad_ui_element_id = 581098
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581098 AND p.ad_tab_id = 549335 AND p.ad_field_id = 781389);
UPDATE ad_ui_element SET ad_ui_element_id = 652505 WHERE ad_ui_element_id = 581098 AND ad_tab_id = 549335 AND ad_field_id = 781389;

-- ad_ui_element 581078 -> 652506 : ad_tab_id = 549336 AND ad_field_id = 781390
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652506
  WHERE ad_ui_element_id = 581078
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581078 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781390);
UPDATE ad_ui_element SET ad_ui_element_id = 652506 WHERE ad_ui_element_id = 581078 AND ad_tab_id = 549336 AND ad_field_id = 781390;

-- ad_ui_element 581079 -> 652507 : ad_tab_id = 549336 AND ad_field_id = 781391
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652507
  WHERE ad_ui_element_id = 581079
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581079 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781391);
UPDATE ad_ui_element SET ad_ui_element_id = 652507 WHERE ad_ui_element_id = 581079 AND ad_tab_id = 549336 AND ad_field_id = 781391;

-- ad_ui_element 581080 -> 652508 : ad_tab_id = 549336 AND ad_field_id = 781392
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652508
  WHERE ad_ui_element_id = 581080
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581080 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781392);
UPDATE ad_ui_element SET ad_ui_element_id = 652508 WHERE ad_ui_element_id = 581080 AND ad_tab_id = 549336 AND ad_field_id = 781392;

-- ad_ui_element 581081 -> 652509 : ad_tab_id = 549336 AND ad_field_id = 781393
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652509
  WHERE ad_ui_element_id = 581081
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581081 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781393);
UPDATE ad_ui_element SET ad_ui_element_id = 652509 WHERE ad_ui_element_id = 581081 AND ad_tab_id = 549336 AND ad_field_id = 781393;

-- ad_ui_element 581082 -> 652510 : ad_tab_id = 549336 AND ad_field_id = 781394
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652510
  WHERE ad_ui_element_id = 581082
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581082 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781394);
UPDATE ad_ui_element SET ad_ui_element_id = 652510 WHERE ad_ui_element_id = 581082 AND ad_tab_id = 549336 AND ad_field_id = 781394;

-- ad_ui_element 581083 -> 652511 : ad_tab_id = 549336 AND ad_field_id = 781395
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652511
  WHERE ad_ui_element_id = 581083
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581083 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781395);
UPDATE ad_ui_element SET ad_ui_element_id = 652511 WHERE ad_ui_element_id = 581083 AND ad_tab_id = 549336 AND ad_field_id = 781395;

-- ad_ui_element 581084 -> 652512 : ad_tab_id = 549336 AND ad_field_id = 781396
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652512
  WHERE ad_ui_element_id = 581084
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581084 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781396);
UPDATE ad_ui_element SET ad_ui_element_id = 652512 WHERE ad_ui_element_id = 581084 AND ad_tab_id = 549336 AND ad_field_id = 781396;

-- ad_ui_element 581099 -> 652513 : ad_tab_id = 549336 AND ad_field_id = 781397
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652513
  WHERE ad_ui_element_id = 581099
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581099 AND p.ad_tab_id = 549336 AND p.ad_field_id = 781397);
UPDATE ad_ui_element SET ad_ui_element_id = 652513 WHERE ad_ui_element_id = 581099 AND ad_tab_id = 549336 AND ad_field_id = 781397;

-- ad_ui_element 581088 -> 652514 : ad_tab_id = 549337 AND ad_field_id = 781398
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652514
  WHERE ad_ui_element_id = 581088
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581088 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781398);
UPDATE ad_ui_element SET ad_ui_element_id = 652514 WHERE ad_ui_element_id = 581088 AND ad_tab_id = 549337 AND ad_field_id = 781398;

-- ad_ui_element 581089 -> 652515 : ad_tab_id = 549337 AND ad_field_id = 781399
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652515
  WHERE ad_ui_element_id = 581089
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581089 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781399);
UPDATE ad_ui_element SET ad_ui_element_id = 652515 WHERE ad_ui_element_id = 581089 AND ad_tab_id = 549337 AND ad_field_id = 781399;

-- ad_ui_element 581090 -> 652516 : ad_tab_id = 549337 AND ad_field_id = 781400
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652516
  WHERE ad_ui_element_id = 581090
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581090 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781400);
UPDATE ad_ui_element SET ad_ui_element_id = 652516 WHERE ad_ui_element_id = 581090 AND ad_tab_id = 549337 AND ad_field_id = 781400;

-- ad_ui_element 581091 -> 652517 : ad_tab_id = 549337 AND ad_field_id = 781401
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652517
  WHERE ad_ui_element_id = 581091
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581091 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781401);
UPDATE ad_ui_element SET ad_ui_element_id = 652517 WHERE ad_ui_element_id = 581091 AND ad_tab_id = 549337 AND ad_field_id = 781401;

-- ad_ui_element 581092 -> 652518 : ad_tab_id = 549337 AND ad_field_id = 781402
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652518
  WHERE ad_ui_element_id = 581092
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581092 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781402);
UPDATE ad_ui_element SET ad_ui_element_id = 652518 WHERE ad_ui_element_id = 581092 AND ad_tab_id = 549337 AND ad_field_id = 781402;

-- ad_ui_element 581093 -> 652519 : ad_tab_id = 549337 AND ad_field_id = 781403
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652519
  WHERE ad_ui_element_id = 581093
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581093 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781403);
UPDATE ad_ui_element SET ad_ui_element_id = 652519 WHERE ad_ui_element_id = 581093 AND ad_tab_id = 549337 AND ad_field_id = 781403;

-- ad_ui_element 581094 -> 652520 : ad_tab_id = 549337 AND ad_field_id = 781404
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652520
  WHERE ad_ui_element_id = 581094
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581094 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781404);
UPDATE ad_ui_element SET ad_ui_element_id = 652520 WHERE ad_ui_element_id = 581094 AND ad_tab_id = 549337 AND ad_field_id = 781404;

-- ad_ui_element 581100 -> 652521 : ad_tab_id = 549337 AND ad_field_id = 781405
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652521
  WHERE ad_ui_element_id = 581100
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581100 AND p.ad_tab_id = 549337 AND p.ad_field_id = 781405);
UPDATE ad_ui_element SET ad_ui_element_id = 652521 WHERE ad_ui_element_id = 581100 AND ad_tab_id = 549337 AND ad_field_id = 781405;

-- ad_ui_element 581186 -> 652523 : ad_tab_id = 549338 AND ad_field_id = 781407
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652523
  WHERE ad_ui_element_id = 581186
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581186 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781407);
UPDATE ad_ui_element SET ad_ui_element_id = 652523 WHERE ad_ui_element_id = 581186 AND ad_tab_id = 549338 AND ad_field_id = 781407;

-- ad_ui_element 581187 -> 652524 : ad_tab_id = 549338 AND ad_field_id = 781408
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652524
  WHERE ad_ui_element_id = 581187
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581187 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781408);
UPDATE ad_ui_element SET ad_ui_element_id = 652524 WHERE ad_ui_element_id = 581187 AND ad_tab_id = 549338 AND ad_field_id = 781408;

-- ad_ui_element 581188 -> 652525 : ad_tab_id = 549338 AND ad_field_id = 781409
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652525
  WHERE ad_ui_element_id = 581188
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581188 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781409);
UPDATE ad_ui_element SET ad_ui_element_id = 652525 WHERE ad_ui_element_id = 581188 AND ad_tab_id = 549338 AND ad_field_id = 781409;

-- ad_ui_element 581189 -> 652526 : ad_tab_id = 549338 AND ad_field_id = 781410
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652526
  WHERE ad_ui_element_id = 581189
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581189 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781410);
UPDATE ad_ui_element SET ad_ui_element_id = 652526 WHERE ad_ui_element_id = 581189 AND ad_tab_id = 549338 AND ad_field_id = 781410;

-- ad_ui_element 581190 -> 652527 : ad_tab_id = 549338 AND ad_field_id = 781411
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652527
  WHERE ad_ui_element_id = 581190
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581190 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781411);
UPDATE ad_ui_element SET ad_ui_element_id = 652527 WHERE ad_ui_element_id = 581190 AND ad_tab_id = 549338 AND ad_field_id = 781411;

-- ad_ui_element 581191 -> 652528 : ad_tab_id = 549338 AND ad_field_id = 781412
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652528
  WHERE ad_ui_element_id = 581191
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581191 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781412);
UPDATE ad_ui_element SET ad_ui_element_id = 652528 WHERE ad_ui_element_id = 581191 AND ad_tab_id = 549338 AND ad_field_id = 781412;

-- ad_ui_element 581192 -> 652529 : ad_tab_id = 549338 AND ad_field_id = 781413
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652529
  WHERE ad_ui_element_id = 581192
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581192 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781413);
UPDATE ad_ui_element SET ad_ui_element_id = 652529 WHERE ad_ui_element_id = 581192 AND ad_tab_id = 549338 AND ad_field_id = 781413;

-- ad_ui_element 581193 -> 652530 : ad_tab_id = 549338 AND ad_field_id = 781414
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652530
  WHERE ad_ui_element_id = 581193
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581193 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781414);
UPDATE ad_ui_element SET ad_ui_element_id = 652530 WHERE ad_ui_element_id = 581193 AND ad_tab_id = 549338 AND ad_field_id = 781414;

-- ad_ui_element 581194 -> 652531 : ad_tab_id = 549338 AND ad_field_id = 781415
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652531
  WHERE ad_ui_element_id = 581194
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581194 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781415);
UPDATE ad_ui_element SET ad_ui_element_id = 652531 WHERE ad_ui_element_id = 581194 AND ad_tab_id = 549338 AND ad_field_id = 781415;

-- ad_ui_element 581195 -> 652532 : ad_tab_id = 549338 AND ad_field_id = 781416
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652532
  WHERE ad_ui_element_id = 581195
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581195 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781416);
UPDATE ad_ui_element SET ad_ui_element_id = 652532 WHERE ad_ui_element_id = 581195 AND ad_tab_id = 549338 AND ad_field_id = 781416;

-- ad_ui_element 581196 -> 652533 : ad_tab_id = 549338 AND ad_field_id = 781417
UPDATE ad_ui_elementfield SET ad_ui_element_id = 652533
  WHERE ad_ui_element_id = 581196
    AND EXISTS (SELECT 1 FROM ad_ui_element p WHERE p.ad_ui_element_id = 581196 AND p.ad_tab_id = 549338 AND p.ad_field_id = 781417);
UPDATE ad_ui_element SET ad_ui_element_id = 652533 WHERE ad_ui_element_id = 581196 AND ad_tab_id = 549338 AND ad_field_id = 781417;

COMMIT;
