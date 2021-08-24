/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

update webui_dashboard d set isactive='N'
WHERE d.isactive='Y'
      and not exists (SELECT 1
       FROM webui_dashboarditem di
                INNER JOIN webui_kpi kpi ON kpi.webui_kpi_id = di.webui_kpi_id
       WHERE di.webui_dashboard_id = d.webui_dashboard_id
         AND kpi.kpi_datasource_type = 'S'
      )
and (select value from ad_sysconfig where name='elastic_enable') = 'N'
;

