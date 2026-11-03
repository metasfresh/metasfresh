-- Run mode: SWING_CLIENT

-- Column: AD_Column.Name
-- 2025-10-23T12:39:54.680Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 12:39:54.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=111
;

-- 2025-10-23T12:40:10.742Z
INSERT INTO t_alter_column values('ad_column','Name','VARCHAR(600)',null,null)
;

-- Column: AD_Element.PO_Description
-- 2025-10-23T12:40:44.294Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2025-10-23 12:40:44.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6283
;

-- 2025-10-23T12:41:02.635Z
INSERT INTO t_alter_column values('ad_element','PO_Description','VARCHAR(2000)',null,null)
;

-- Column: AD_Element.ColumnName
-- 2025-10-23T12:41:34.345Z
UPDATE AD_Column SET FieldLength=200,Updated=TO_TIMESTAMP('2025-10-23 12:41:34.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2602
;

-- 2025-10-23T12:41:52.033Z
INSERT INTO t_alter_column values('ad_element','ColumnName','VARCHAR(200)',null,null)
;

-- Column: AD_Element_Trl.WEBUI_NameBrowse
-- 2025-10-23T12:42:23.875Z
UPDATE AD_Column SET FieldLength=1000,Updated=TO_TIMESTAMP('2025-10-23 12:42:23.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=563266
;

-- 2025-10-23T12:43:39.682Z
INSERT INTO t_alter_column values('ad_element_trl','WEBUI_NameBrowse','VARCHAR(1000)',null,null)
;

-- Column: AD_Element_Trl.WEBUI_NameNew
-- 2025-10-23T12:44:46.665Z
UPDATE AD_Column SET FieldLength=1000,Updated=TO_TIMESTAMP('2025-10-23 12:44:46.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=563267
;

-- 2025-10-23T12:45:03.889Z
INSERT INTO t_alter_column values('ad_element_trl','WEBUI_NameNew','VARCHAR(1000)',null,null)
;

-- Column: AD_Element_Trl.WEBUI_NameNewBreadcrumb
-- 2025-10-23T12:45:21.905Z
UPDATE AD_Column SET FieldLength=1000,Updated=TO_TIMESTAMP('2025-10-23 12:45:21.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=563268
;

-- 2025-10-23T12:45:38.297Z
INSERT INTO t_alter_column values('ad_element_trl','WEBUI_NameNewBreadcrumb','VARCHAR(1000)',null,null)
;

-- Column: AD_Field.Name
-- 2025-10-23T12:47:09.998Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 12:47:09.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=168
;

-- 2025-10-23T12:47:27.544Z
INSERT INTO t_alter_column values('ad_field','Name','VARCHAR(600)',null,null)
;

-- 2025-10-23T12:47:27.723Z
INSERT INTO t_alter_column values('ad_field','Name',null,'NULL',null)
;

-- Column: AD_Menu.WEBUI_NameNew
-- 2025-10-23T12:48:24.846Z
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2025-10-23 12:48:24.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=555049
;

-- 2025-10-23T12:48:41.747Z
INSERT INTO t_alter_column values('ad_menu','WEBUI_NameNew','VARCHAR(250)',null,null)
;

-- Column: AD_Menu.Name
-- 2025-10-23T12:48:54.492Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2025-10-23 12:48:54.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=229
;

-- 2025-10-23T12:49:10.269Z
INSERT INTO t_alter_column values('ad_menu','Name','VARCHAR(120)',null,null)
;

-- Column: AD_Menu.WEBUI_NameNewBreadcrumb
-- 2025-10-23T12:49:27.615Z
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2025-10-23 12:49:27.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=555052
;

-- 2025-10-23T12:50:47.893Z
INSERT INTO t_alter_column values('ad_menu','WEBUI_NameNewBreadcrumb','VARCHAR(250)',null,null)
;

-- Column: AD_Menu.WEBUI_NameBrowse
-- 2025-10-23T12:51:05.400Z
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2025-10-23 12:51:05.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=555048
;

-- 2025-10-23T12:51:19.408Z
INSERT INTO t_alter_column values('ad_menu','WEBUI_NameBrowse','VARCHAR(250)',null,null)
;

-- Column: AD_Process.Name
-- 2025-10-23T12:52:04.717Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 12:52:04.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2809
;

-- 2025-10-23T12:52:19.427Z
INSERT INTO t_alter_column values('ad_process','Name','VARCHAR(600)',null,null)
;

-- Column: AD_Process_Trl.Description
-- 2025-10-23T12:57:39.900Z
UPDATE AD_Column SET FieldLength=276447231,Updated=TO_TIMESTAMP('2025-10-23 12:57:39.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2853
;

-- 2025-10-23T12:57:58.027Z
INSERT INTO t_alter_column values('ad_process_trl','Description','TEXT',null,null)
;

-- Column: AD_Process_Trl.Help
-- 2025-10-23T12:58:09.828Z
UPDATE AD_Column SET FieldLength=276447231,Updated=TO_TIMESTAMP('2025-10-23 12:58:09.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2854
;

-- 2025-10-23T12:58:28.129Z
INSERT INTO t_alter_column values('ad_process_trl','Help','TEXT',null,null)
;

-- Column: AD_Process_Para.Name
-- 2025-10-23T12:59:14.574Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 12:59:14.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2822
;

-- 2025-10-23T12:59:31.304Z
INSERT INTO t_alter_column values('ad_process_para','Name','VARCHAR(600)',null,null)
;

-- Column: AD_Reference.Name
-- 2025-10-23T13:00:03.768Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 13:00:03.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=130
;

-- 2025-10-23T13:00:20.601Z
INSERT INTO t_alter_column values('ad_reference','Name','VARCHAR(600)',null,null)
;

-- Column: AD_Reference.Description
-- 2025-10-23T13:00:35.205Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2025-10-23 13:00:35.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=131
;

-- 2025-10-23T13:00:51.279Z
INSERT INTO t_alter_column values('ad_reference','Description','VARCHAR(2000)',null,null)
;

-- Column: AD_Ref_List.Name
-- 2025-10-23T13:01:18.313Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 13:01:18.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=149
;

-- 2025-10-23T13:01:33.298Z
INSERT INTO t_alter_column values('ad_ref_list','Name','VARCHAR(600)',null,null)
;

-- Column: AD_Ref_List.Description
-- 2025-10-23T13:02:30.768Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2025-10-23 13:02:30.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=150
;

-- 2025-10-23T13:02:45.212Z
INSERT INTO t_alter_column values('ad_ref_list','Description','VARCHAR(2000)',null,null)
;

-- Column: AD_Tab.Name
-- 2025-10-23T13:03:27.943Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2025-10-23 13:03:27.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=161
;

-- 2025-10-23T13:03:44.534Z
INSERT INTO t_alter_column values('ad_tab','Name','VARCHAR(120)',null,null)
;

-- Column: AD_UI_Section_Trl.Description
-- 2025-10-23T13:04:14.942Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2025-10-23 13:04:14.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=556827
;

-- 2025-10-23T13:04:29.236Z
INSERT INTO t_alter_column values('ad_ui_section_trl','Description','VARCHAR(2000)',null,null)
;

-- Column: AD_Window.Name
-- 2025-10-23T13:05:35.715Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2025-10-23 13:05:35.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=156
;

INSERT INTO t_alter_column values('ad_window','Name','VARCHAR(120)',null,null)
;

-- Column: C_DocType_Trl.DocumentNote
-- 2025-10-23T13:06:13.316Z
UPDATE AD_Column SET FieldLength=2048,Updated=TO_TIMESTAMP('2025-10-23 13:06:13.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3135
;

-- 2025-10-23T13:06:27.723Z
INSERT INTO t_alter_column values('c_doctype_trl','DocumentNote','VARCHAR(2048)',null,null)
;

-- Column: C_ElementValue.Name
-- 2025-10-23T13:07:14.105Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-10-23 13:07:14.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=1135
;

-- 2025-10-23T13:07:30.508Z
INSERT INTO t_alter_column values('c_elementvalue','Name','VARCHAR(255)',null,null)
;

-- Column: C_Period_Trl.Name
-- 2025-10-23T13:07:56.889Z
--UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2025-10-23 13:07:56.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=554381
--;

-- 2025-10-23T13:08:11.647Z
--INSERT INTO t_alter_column values('c_period_trl','Name','VARCHAR(60)',null,null)
--;

-- Column: C_Tax.Name
-- 2025-10-23T13:08:50.867Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-10-23 13:08:50.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2246
;

-- 2025-10-23T13:09:09.310Z
INSERT INTO t_alter_column values('c_tax','Name','VARCHAR(255)',null,null)
;

-- Column: M_Additive.Name
-- 2025-10-23T13:09:40.414Z
--UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2025-10-23 13:09:40.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=578767
--;

-- 2025-10-23T13:09:56.904Z
--INSERT INTO t_alter_column values('m_additive','Name','VARCHAR(60)',null,null)
--;

-- Column: M_Allergen.Name
-- 2025-10-23T13:10:20.347Z
--UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2025-10-23 13:10:20.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560689
--;

-- 2025-10-23T13:10:36.114Z
--INSERT INTO t_alter_column values('m_allergen','Name','VARCHAR(60)',null,null)
--;

-- Column: M_Allergen_Trace.Description
-- 2025-10-23T13:11:02.043Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-10-23 13:11:02.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=571059
;

-- 2025-10-23T13:11:17.941Z
INSERT INTO t_alter_column values('m_allergen_trace','Description','VARCHAR(255)',null,null)
;

-- Column: M_Allergen_Trace.Name
-- 2025-10-23T13:11:31.110Z
--UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2025-10-23 13:11:31.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=571058
--;

-- 2025-10-23T13:11:48.933Z
--INSERT INTO t_alter_column values('m_allergen_trace','Name','VARCHAR(60)',null,null)
--;

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

-- Column: M_FoodAdvice.Name
-- 2025-10-23T13:12:10.414Z
--UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2025-10-23 13:12:10.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583323
--;

-- 2025-10-23T13:12:28.730Z
--INSERT INTO t_alter_column values('m_foodadvice','Name','VARCHAR(60)',null,null)
--;

-- Column: M_HazardSymbol_Trl.Name
-- 2025-10-23T13:13:05.080Z
UPDATE AD_Column SET FieldLength=250,Updated=TO_TIMESTAMP('2025-10-23 13:13:05.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=578827
;

-- 2025-10-23T13:13:22.161Z
INSERT INTO t_alter_column values('m_hazardsymbol_trl','Name','VARCHAR(250)',null,null)
;

-- Column: M_Product_Trl.Name
-- 2025-10-23T13:13:47.346Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2025-10-23 13:13:47.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3330
;

-- 2025-10-23T13:14:02.212Z
INSERT INTO t_alter_column values('m_product_trl','Name','VARCHAR(600)',null,null)
;

-- Column: M_Product_Trl.CustomerLabelName
-- 2025-10-23T13:14:15.881Z
UPDATE AD_Column SET FieldLength=1999,Updated=TO_TIMESTAMP('2025-10-23 13:14:15.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=559492
;

-- 2025-10-23T13:14:32.186Z
INSERT INTO t_alter_column values('m_product_trl','CustomerLabelName','VARCHAR(1999)',null,null)
;

-- Column: R_Status_Trl.Help
-- 2025-10-23T13:15:06.494Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2025-10-23 13:15:06.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560796
;

-- 2025-10-23T13:15:21.750Z
INSERT INTO t_alter_column values('r_status_trl','Help','VARCHAR(2000)',null,null)
;

