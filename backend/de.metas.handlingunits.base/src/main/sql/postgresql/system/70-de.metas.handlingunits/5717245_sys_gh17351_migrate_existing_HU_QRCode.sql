/*
 * #%L
 * de.metas.handlingunits.base
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

insert into m_hu_qrcode_assignment(m_hu_qrcode_assignment_id, m_hu_id, m_hu_qrcode_id, ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby)
select NEXTVAL('m_hu_qrcode_assignment_seq'), m_hu_id, m_hu_qrcode_id, ad_client_id, ad_org_id, created, 99, isactive, now()::timestamp with time zone, 99 from m_hu_qrcode;