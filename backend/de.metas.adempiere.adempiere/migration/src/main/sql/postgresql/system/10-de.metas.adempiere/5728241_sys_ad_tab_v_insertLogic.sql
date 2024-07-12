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

DROP VIEW IF EXISTS ad_tab_v
;

CREATE OR REPLACE VIEW ad_tab_v AS
SELECT
    (select bl.ad_language from ad_language bl where bl.isbaselanguage='Y' order by isactive desc limit 1), -- trl.ad_language
    t.ad_tab_id
     , t.Template_Tab_ID
     , t.ad_window_id
     , t.InternalName
     , t.ad_table_id
     , t.name
     , t.description
     , t.help
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
     , 'N'::bpchar AS hasassociation
     , t.istranslationtab
     , t.isreadonly
     , t.ad_image_id
     , t.tablevel
     , t.whereclause
     , t.orderbyclause
     , t.commitwarning
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
     , t.QuickInput_OpenButton_Caption
     , t.QuickInput_CloseButton_Caption
     , t.IncludedTabNewRecordInputMode
     , t.IsRefreshViewOnChangeEvents
     , t.IsAutodetectDefaultDateFilter
     , t.QuickInputLayout
FROM ad_tab t
         JOIN ad_table tbl ON t.ad_table_id = tbl.ad_table_id
WHERE t.isactive = 'Y'::bpchar
  AND tbl.isactive = 'Y'::bpchar
;
