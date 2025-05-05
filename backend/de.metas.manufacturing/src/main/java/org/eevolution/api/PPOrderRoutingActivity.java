package org.eevolution.api;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPOrderTargetPlanningStatus;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingActivityTemplateId;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.material.planning.pporder.UserInstructions;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.workflow.WFDurationUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter(AccessLevel.PRIVATE)
public final class PPOrderRoutingActivity
{
	private static final Logger logger = LogManager.getLogger(PPOrderRoutingActivity.class);

	@Nullable private PPOrderRoutingActivityId id;
	@NonNull private final PPRoutingActivityType type;
	@NonNull private final PPOrderRoutingActivityCode code;
	@NonNull private final String name;
	@NonNull private final PPRoutingActivityId routingActivityId;

	private final boolean subcontracting;
	@Nullable private final BPartnerId subcontractingVendorId;

	private final boolean milestone;
	@NonNull private final PPAlwaysAvailableToUser alwaysAvailableToUser;
	@Nullable private final UserInstructions userInstructions;
	@Nullable private final PPOrderTargetPlanningStatus targetPlanningStatus;

	@Nullable private PPRoutingActivityTemplateId activityTemplateId;

	@NonNull private final ResourceId resourceId;

	@NonNull
	@Default
	@Setter(AccessLevel.PRIVATE)
	private PPOrderRoutingActivityStatus status = PPOrderRoutingActivityStatus.NOT_STARTED;

	//
	// Standard values
	@NonNull private final WFDurationUnit durationUnit;
	@NonNull private final Duration queuingTime;
	@NonNull private final Duration setupTime;
	@NonNull private final Duration waitingTime;
	@NonNull private final Duration movingTime;
	@NonNull private final Duration durationPerOneUnit;
	/**
	 * how many items can be manufactured on a production line in given duration unit.
	 */
	private final int unitsPerCycle;

	//
	// Planned values
	@NonNull private Duration setupTimeRequired;
	@NonNull private Duration durationRequired;
	@NonNull private Quantity qtyRequired;

	//
	// Reported values
	@NonNull @Default private Duration setupTimeReal = Duration.ZERO;
	@NonNull @Default private Duration durationReal = Duration.ZERO;
	@NonNull private Quantity qtyDelivered;
	@NonNull private Quantity qtyScrapped;
	@NonNull private Quantity qtyRejected;
	@Nullable private Instant dateStart;
	@Nullable private Instant dateFinish;

	//
	// Data needed for PPRoutingActivityType.CallExternalSystem and other steps which are about scanning QR codes
	@Setter @Nullable private GlobalQRCode scannedQRCode;

	public PPOrderRoutingActivity copy() {return toBuilder().build();}

	public PPOrderId getOrderId()
	{
		return Objects.requireNonNull(getId()).getOrderId();
	}

	public Quantity getQtyToDeliver()
	{
		return getQtyRequired().subtract(getQtyDelivered());
	}

	public Duration getSetupTimeRemaining()
	{
		return getSetupTimeRequired().minus(getSetupTimeReal());
	}

	public Duration getDurationTotalBooked() {return getSetupTimeReal().plus(getDurationReal());}

	public boolean isSomethingProcessed()
	{
		return getQtyDelivered().signum() != 0
				|| getQtyScrapped().signum() != 0
				|| getQtyRejected().signum() != 0
				|| getSetupTimeReal().toNanos() != 0
				|| getDurationReal().toNanos() != 0;
	}

	/**
	 * DON'T use it directly
	 */
	public void setId(final PPOrderRoutingActivityId id)
	{
		if (this.id == null)
		{
			this.id = id;
		}
		else
		{
			Check.assumeEquals(this.id, id);
		}
	}

	public void changeStatusTo(@NonNull final PPOrderRoutingActivityStatus newStatus)
	{
		final PPOrderRoutingActivityStatus currentStatus = getStatus();
		if (currentStatus.equals(newStatus))
		{
			return;
		}

		currentStatus.assertCanChangeTo(newStatus);
		this.status = newStatus;
	}

	void reportProgress(final PPOrderActivityProcessReport report)
	{
		changeStatusTo(PPOrderRoutingActivityStatus.IN_PROGRESS);

		if (getDateStart() == null)
		{
			setDateStart(report.getFinishDate().minus(report.getDuration()));
		}
		if (getDateFinish() == null || getDateFinish().isBefore(report.getFinishDate()))
		{
			setDateFinish(report.getFinishDate());
		}

		if (report.getQtyProcessed() != null)
		{
			setQtyDelivered(getQtyDelivered().add(report.getQtyProcessed()));
		}
		if (report.getQtyScrapped() != null)
		{
			setQtyScrapped(getQtyScrapped().add(report.getQtyScrapped()));
		}
		if (report.getQtyRejected() != null)
		{
			setQtyRejected(getQtyRejected().add(report.getQtyRejected()));
		}

		setDurationReal(getDurationReal().plus(report.getDuration()));
		setSetupTimeReal(getSetupTimeReal().plus(report.getSetupTime()));
	}

	void closeIt()
	{
		if (getStatus() == PPOrderRoutingActivityStatus.CLOSED)
		{
			logger.warn("Activity already closed - {}", this);
			return;
		}
		else if (getStatus() == PPOrderRoutingActivityStatus.IN_PROGRESS)
		{
			completeIt();
		}

		changeStatusTo(PPOrderRoutingActivityStatus.CLOSED);

		if (getDateFinish() != null)
		{
			setDateFinish(SystemTime.asInstant());
		}

		if (!Objects.equals(getDurationRequired(), getDurationReal()))
		{
			// addDescription(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@closed@ ( @Duration@ :" + getDurationRequiered() + ") ( @QtyRequiered@ :" + getQtyRequiered() + ")"));
			setDurationRequired(getDurationReal());
			setQtyRequired(getQtyDelivered());
		}
	}

	void uncloseIt()
	{
		if (getStatus() != PPOrderRoutingActivityStatus.CLOSED)
		{
			logger.warn("Only Closed activities can be unclosed - {}", this);
			return;
		}

		changeStatusTo(PPOrderRoutingActivityStatus.IN_PROGRESS);
	}

	void voidIt()
	{
		if (getStatus() == PPOrderRoutingActivityStatus.VOIDED)
		{
			logger.warn("Activity already voided - {}", this);
			return;
		}

		changeStatusTo(PPOrderRoutingActivityStatus.VOIDED);

		setQtyRequired(getQtyRequired().toZero());
		setSetupTimeRequired(Duration.ZERO);
		setDurationRequired(Duration.ZERO);
	}

	public void completeIt()
	{
		changeStatusTo(PPOrderRoutingActivityStatus.COMPLETED);

		if (getDateFinish() == null)
		{
			setDateFinish(SystemTime.asInstant());
		}
	}

}
