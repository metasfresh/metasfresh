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

-- 2022-04-26T12:57:07.996Z
UPDATE AD_Ref_List SET Description='Local events are posted to a specific RabbitMQ queue and only local subscribers are notified.',Updated=TO_TIMESTAMP('2022-04-26 15:57:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541509
;

-- 2022-04-26T12:57:15.438Z
UPDATE AD_Ref_List SET Description='Distributed events are posted to all the RabbitMQ queues bound to the given exchange and all subscribers are notified (on all machines).',Updated=TO_TIMESTAMP('2022-04-26 15:57:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543155
;

