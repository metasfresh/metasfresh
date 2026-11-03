/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

-- adding "..OR AD_Process.Value ILIKE '%HU_QRCode%'"
UPDATE AD_Val_Rule SET Code='AD_Process.AD_Process_ID IN (540370, 540412, 540413, 540414, 540415, 540416, 540933, 541195, 584694, 585387) OR AD_Process.Value ILIKE ''HU_Label_%'' OR AD_Process.Value ILIKE ''%HU_QRCode%''', updatedby=100, updated='2026-06-16 00:00' where AD_Val_Rule_ID=540604;        