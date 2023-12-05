/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

UPDATE C_Queue_Processor SET description='Events not handled anymore through a workpackage. Deactivated and not deleted to avoid foreign key constraint fails.', UpdatedBy=99, Updated='2022-04-26 13:55:47.057538+03' WHERE c_queue_processor_id = 540064
;

UPDATE C_Queue_PackageProcessor SET description='Events not handled anymore through a workpackage. Deactivated and not deleted to avoid foreign key constraint fails.', UpdatedBy=99, Updated='2022-04-26 13:55:47.057538+03' WHERE C_Queue_PackageProcessor_ID = 540094
;