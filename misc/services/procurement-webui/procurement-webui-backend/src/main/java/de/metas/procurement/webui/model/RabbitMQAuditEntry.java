/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.procurement.webui.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rabbitmq_audit_entry")
@Getter
@Setter
public class RabbitMQAuditEntry extends AbstractEntity
{
	private String queue;
	private String direction;

	public static final int CONTENT_LENGTH = 9999999;
	@Column(length = CONTENT_LENGTH)
	private String content;

	private String eventId;
	private String relatedEventId;
}
