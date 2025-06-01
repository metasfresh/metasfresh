package de.metas.vertical.pharma.msv3.server.order.jpa;

import de.metas.vertical.pharma.msv3.protocol.order.OrderStatus;
import de.metas.vertical.pharma.msv3.server.jpa.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
@Table(name = "msv3_order", //
		uniqueConstraints = @UniqueConstraint(name = "order_uq", columnNames = { "documentNo", "mf_bpartner_id" }),
		indexes = @Index(name = "order_bpartner_id", columnList = "mf_bpartner_id", unique = false))
@Getter
@Setter
@ToString
public class JpaOrder extends AbstractEntity
{
	@Column(name = "mf_bpartner_id")
	@NotNull
	private Integer mfBpartnerId;

	@Column(name = "mf_bpartnerlocation_id")
	@NotNull
	private Integer mfBpartnerLocationId;

	@NotNull
	private String documentNo;
	@NotNull
	private Integer supportId;
	@NotNull
	private Boolean nightOperation;
	@NotNull
	private OrderStatus orderStatus;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private final List<JpaOrderPackage> orderPackages = new ArrayList<>();

	//
	//
	private boolean syncSent;
	private Instant syncSentTS;

	private boolean mfSyncAck;
	private Instant syncAckTS;

	private boolean mfSyncError;
	private String mfSyncErrorMsg;
	private Instant syncErrorTS;

	public void addOrderPackages(@NonNull final List<JpaOrderPackage> orderPackages)
	{
		orderPackages.forEach(orderPackage -> orderPackage.setOrder(this));
		this.orderPackages.addAll(orderPackages);
	}

	public void visitItems(@NonNull final Consumer<JpaOrderPackageItem> consumer)
	{
		orderPackages.stream()
				.flatMap(orderPackage -> orderPackage.getItems().stream())
				.forEach(consumer);
	}

	public void markSyncSent()
	{
		syncSent = true;
		syncSentTS = Instant.now();
	}

	public void markSyncAck()
	{
		mfSyncAck = true;
		syncAckTS = Instant.now();
	}

	public void markSyncError(final String errorMsg)
	{
		mfSyncError = true;
		mfSyncErrorMsg = errorMsg;
		syncErrorTS = Instant.now();
	}
}
