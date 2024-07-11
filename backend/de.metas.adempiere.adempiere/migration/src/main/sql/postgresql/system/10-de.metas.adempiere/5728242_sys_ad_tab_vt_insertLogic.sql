/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2024 metas GmbH
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

DROP VIEW IF EXISTS ad_tab_vt
;

CREATE OR REPLACE VIEW ad_tab_vt AS
SELECT trl.ad_language
     , t.ad_tab_id
     , t.Template_Tab_ID
     , t.ad_window_id
     , t.InternalName
     , t.ad_table_id
     , trl.name
     , t.name                           AS Name_BaseLang
     , trl.description
     , t.description                    AS Description_BaseLang
     , trl.help
     , t.help                           AS Help_BaseLang
     , t.seqno
     , t.issinglerow
     , t.hastree
     , t.isinfotab
     , tbl.replicationtype
     , tbl.tablename
     , tbl.accesslevel
     , tbl.issecurityenabled
     , tbl.isdeleteable
     , tbl.ishighvolume
     , tbl.isview
     , 'N'::bpchar                      AS hasassociation
     , t.istranslationtab
     , t.isreadonly
     , t.ad_image_id
     , t.tablevel
     , t.whereclause
     , t.orderbyclause
     , trl.commitwarning
     , t.CommitWarning                  AS CommitWarning_BaseLang
     , t.readonlylogic
     , t.displaylogic
     , t.ad_column_id
     , t.ad_process_id
     , t.issorttab
     , t.isinsertrecord
     , t.insertlogic
     , t.isadvancedtab
     , t.ad_columnsortorder_id
     , t.ad_columnsortyesno_id
     , t.included_tab_id
     , t.parent_column_id
     , t.isrefreshallonactivate
     , t.issearchactive
     , t.defaultwhereclause
     , t.issearchcollapsed
     , t.isqueryonload
     , t.IsQueryIfNoFilters
     , t.isgridmodeonly
     , t.ad_message_id
     , t.ischeckparentschanged
     , t.maxqueryrecords
     , t.EntityType
     , t.AllowQuickInput
     , trl.QuickInput_OpenButton_Caption
     , t.QuickInput_OpenButton_Caption  AS QuickInput_OpenButton_Caption_BaseLang
     , trl.QuickInput_CloseButton_Caption
     , t.QuickInput_CloseButton_Caption AS QuickInput_CloseButton_Caption_BaseLang
     , t.IncludedTabNewRecordInputMode
     , t.IsRefreshViewOnChangeEvents
     , t.IsAutodetectDefaultDateFilter
     , t.QuickInputLayout
     , trl.NotFound_Message
     , t.NotFound_Message AS NotFound_Message_BaseLang
     , trl.NotFound_MessageDetail
     , t.NotFound_MessageDetail AS NotFound_MessageDetail_BaseLang
     , t.IncludeFiltersStrategy
FROM ad_tab t
         JOIN ad_table tbl ON t.ad_table_id = tbl.ad_table_id
         JOIN ad_tab_trl trl ON t.ad_tab_id = trl.ad_tab_id
WHERE t.isactive = 'Y'::bpchar
  AND tbl.isactive = 'Y'::bpchar
;

