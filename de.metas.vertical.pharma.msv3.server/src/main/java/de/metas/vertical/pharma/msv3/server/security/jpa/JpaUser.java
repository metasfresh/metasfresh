package de.metas.vertical.pharma.msv3.server.security.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import de.metas.vertical.pharma.msv3.server.jpa.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Entity
@Table(name = "msv3_user", //
		uniqueConstraints = { //
				@UniqueConstraint(name = "mf_msv3user_id_uq", columnNames = { "mf_msv3user_id" }), //
				@UniqueConstraint(name = "mf_username_uq", columnNames = { "mf_username" }) }, //
		indexes = @Index(name = "mf_sync_token", columnList = "mf_sync_token", unique = false))
@Getter
@Setter
@ToString
public class JpaUser extends AbstractEntity
{
	// kundenkennung
	@Column(name = "mf_username")
	@NotNull
	private String mfUsername;

	@Column(name = "mf_password")
	@NotNull
	private String mfPassword;

	@Column(name = "mf_bpartner_id")
	@NotNull
	private Integer mfBpartnerId;

	@Column(name = "mf_bpartnerlocation_id")
	@NotNull
	private Integer mfBpartnerLocationId;

	@Column(name = "mf_msv3user_id")
	@NotNull
	private Integer mfMSV3UserId;

	@Column(name = "mf_sync_token", nullable = false)
	@NotNull
	private String mfSyncToken;
}
