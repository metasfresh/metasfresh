/*
 * #%L
 * de.metas.swat.base
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


UPDATE ad_field
SET displaylogic=REPLACE(displaylogic, '@DocSubType@', '@DocSubType/X@'), UpdatedBy=100, Updated='2025-11-15 00:39'
WHERE displaylogic ILIKE '%@DocSubType@%'
;

UPDATE ad_field
SET displaylogic=REPLACE(displaylogic, '@DocBaseType@', '@DocBaseType/X@'), UpdatedBy=100, Updated='2025-11-15 00:39'
WHERE displaylogic ILIKE '%@DocBaseType@%'
;
