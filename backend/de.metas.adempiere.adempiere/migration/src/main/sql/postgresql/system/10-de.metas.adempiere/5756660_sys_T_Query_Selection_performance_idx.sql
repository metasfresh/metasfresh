/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- remove stale records
delete from T_Query_Selection_Pagination where T_Query_Selection_Pagination.result_time<(now() - interval '3 days');

CREATE INDEX if not exists t_query_selection_todelete_uuid
    ON t_query_selection_todelete (uuid);
comment on index t_query_selection_todelete_uuid is 'needed to JOIN records from T_Query_Selection_Pagination';

CREATE INDEX if not exists T_Query_Selection_Pagination_uuid
    ON T_Query_Selection_Pagination (uuid);
comment on index T_Query_Selection_Pagination_uuid is 'needed to JOIN records from t_query_selection_todelete';
