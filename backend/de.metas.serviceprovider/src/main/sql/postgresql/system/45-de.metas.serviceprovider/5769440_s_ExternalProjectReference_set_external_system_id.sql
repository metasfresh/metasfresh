/*
 * #%L
 * de.metas.serviceprovider.base
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

SELECT backup_table('s_externalprojectreference', '_ref_list_drop')
;

UPDATE s_externalprojectreference epr
SET externalsystem_id = e.externalsystem_id
FROM s_externalprojectreference epr_source
         JOIN externalsystem e ON e.value = epr_source.externalsystem
WHERE epr.s_externalprojectreference_id = epr_source.s_externalprojectreference_id
;
