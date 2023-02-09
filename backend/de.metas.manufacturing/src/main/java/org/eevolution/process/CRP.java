package org.eevolution.process;

import de.metas.common.util.time.SystemTime;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.WorkingTime;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderActivityScheduleChangeRequest;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityCode;
import org.eevolution.api.PPOrderScheduleChangeRequest;
import org.eevolution.api.PPOrderScheduleChangeRequest.PPOrderScheduleChangeRequestBuilder;
import org.eevolution.exceptions.CRPException;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.reasoner.CRPReasoner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Capacity Requirement Planning
 *
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany (Original by Victor Perez, e-Evolution, S.C.)
 * @author Teo Sarca, www.arhipac.ro
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class CRP extends JavaProcess
{
	private final ResourceService resourceService = SpringContextHolder.instance.getBean(ResourceService.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final IPPOrderBL ordersService = Services.get(IPPOrderBL.class);
	private final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);

	public static final String FORWARD_SCHEDULING = "F";
	public static final String BACKWARD_SCHEDULING = "B";

	private ResourceId plantId;
	private String p_ScheduleType;

	/**
	 * SysConfig parameter - maximum number of algorithm iterations
	 */
	private int p_MaxIterationsNo = -1;
	public static final String SYSCONFIG_MaxIterationsNo = "CRP.MaxIterationsNo";
	public static final int DEFAULT_MaxIterationsNo = 1000;

	/**
	 * CRP Reasoner
	 */
	private CRPReasoner reasoner;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			if (name.equals("S_Resource_ID"))
			{
				plantId = ResourceId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals("ScheduleType"))
			{
				p_ScheduleType = para.getParameterAsString();
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}

		p_MaxIterationsNo = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_MaxIterationsNo, DEFAULT_MaxIterationsNo, getAD_Client_ID());
	}

	@Override
	protected String doIt()
	{
		reasoner = new CRPReasoner();

		reasoner.streamOpenPPOrderIdsOrderedByDatePromised(plantId)
				.forEach(this::runCRP);

		return MSG_OK;
	}

	public void runCRP(final I_PP_Order order)
	{
		try
		{
			final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
			final PPOrderRouting orderRouting = orderRoutingsRepo.getByOrderId(orderId);

			// Schedule Fordward
			PPOrderScheduleChangeRequest changeRequest;
			if (p_ScheduleType.equals(FORWARD_SCHEDULING))
			{
				final Instant orderDateStartSchedule = order.getDateStartSchedule().toInstant();
				changeRequest = scheduleForward(orderRouting, orderDateStartSchedule);
			}
			// Schedule backward
			else if (p_ScheduleType.equals(BACKWARD_SCHEDULING))
			{
				final Instant orderDateEndSchedule = order.getDateFinishSchedule().toInstant();
				changeRequest = scheduleBackward(orderRouting, orderDateEndSchedule);
			}
			else
			{
				throw new CRPException("Unknown scheduling method - " + p_ScheduleType);
			}

			ordersService.changeScheduling(changeRequest);
		}
		catch (final Exception ex)
		{
			throw CRPException.wrapIfNeeded(ex)
					.setPP_Order(order);
		}

	}

	private PPOrderScheduleChangeRequest scheduleForward(@NonNull final PPOrderRouting orderRouting, @NonNull final Instant orderDateStartSchedule)
	{
		final PPOrderScheduleChangeRequestBuilder changeRequest = PPOrderScheduleChangeRequest.builder()
				.orderId(orderRouting.getPpOrderId())
				.scheduledStartDate(orderDateStartSchedule);

		PPOrderRoutingActivity activity = orderRouting.getFirstActivity();
		Instant date = orderDateStartSchedule;
		final Set<PPOrderRoutingActivityCode> visitedActivityCodes = new HashSet<>();
		while (activity != null)
		{
			if (!visitedActivityCodes.add(activity.getCode()))
			{
				throw new CRPException("Cyclic transition found")
						.setOrderActivity(activity);
			}

			//
			// Skip this node if there is no resource
			final ResourceId resourceId = activity.getResourceId();
			if (resourceId == null)
			{
				activity = orderRouting.getNextActivityOrNull(activity);
				continue;
			}

			final Resource resource = resourceService.getResourceById(resourceId);
			if (!reasoner.isAvailable(resource))
			{
				throw new CRPException("@ResourceNotInSlotDay@").setResource(resource);
			}

			final Duration activityDuration = calculateActivityDuration(activity);
			final Instant dateFinish = scheduleForward(date, activityDuration, resource);

			changeRequest.activityChangeRequest(PPOrderActivityScheduleChangeRequest.builder()
					.orderRoutingActivityId(activity.getId())
					.scheduledStartDate(date)
					.scheduledEndDate(dateFinish)
					.build());

			date = dateFinish;
			activity = orderRouting.getNextActivityOrNull(activity);
		}

		changeRequest.scheduledEndDate(date);

		return changeRequest.build();
	}

	private PPOrderScheduleChangeRequest scheduleBackward(@NonNull final PPOrderRouting orderRouting, @NonNull final Instant orderDateEndSchedule)
	{
		final PPOrderScheduleChangeRequestBuilder changeRequest = PPOrderScheduleChangeRequest.builder()
				.orderId(orderRouting.getPpOrderId())
				.scheduledEndDate(orderDateEndSchedule);

		PPOrderRoutingActivity activity = orderRouting.getLastActivity();
		Instant date = orderDateEndSchedule;
		final Set<PPOrderRoutingActivityCode> visitedActivityCodes = new HashSet<>();
		while (activity != null)
		{
			if (!visitedActivityCodes.add(activity.getCode()))
			{
				throw new CRPException("Cyclic transition found - ").setOrderActivity(activity);
			}

			//
			// Skip this node if there is no resource
			final ResourceId resourceId = activity.getResourceId();
			if (resourceId == null)
			{
				activity = orderRouting.getPreviousActivityOrNull(activity);
				continue;
			}

			final Resource resource = resourceService.getResourceById(resourceId);
			if (!reasoner.isAvailable(resource))
			{
				throw new CRPException("@ResourceNotInSlotDay@").setResource(resource);
			}

			final Duration activityDuration = calculateActivityDuration(activity);
			final Instant dateStart = scheduleBackward(date, activityDuration, resource);

			changeRequest.activityChangeRequest(PPOrderActivityScheduleChangeRequest.builder()
					.orderRoutingActivityId(activity.getId())
					.scheduledStartDate(dateStart)
					.scheduledEndDate(date)
					.build());

			date = dateStart;
			activity = orderRouting.getPreviousActivityOrNull(activity);
		}

		changeRequest.scheduledStartDate(date);

		return changeRequest.build();
	}

	/**
	 * Calculate how many millis take to complete given qty on given node(operation).
	 *
	 * @param activity operation
	 * @return duration in millis
	 */
	private Duration calculateActivityDuration(final PPOrderRoutingActivity activity)
	{
		final Quantity qty = activity.getQtyToDeliver();
		// Total duration of workflow node (seconds) ...
		// ... its static single parts ...
		Duration totalDuration = activity.getQueuingTime()
				.plus(activity.getSetupTimeRequired()) // Use the present required setup time to notice later changes
				.plus(activity.getMovingTime())
				.plus(activity.getWaitingTime());
		// ... and its qty dependent working time ... (Use the present required duration time to notice later changes)
		final WorkingTime workingTime = WorkingTime.builder()
				.durationPerOneUnit(activity.getDurationPerOneUnit())
				.unitsPerCycle(activity.getUnitsPerCycle())
				.qty(qty)
				.activityTimeUnit(activity.getDurationUnit())
				.build();
		totalDuration = totalDuration.plus(workingTime.getDuration());

		return totalDuration;
	}

	/**
	 * Calculate duration in millis
	 *
	 * @param dayStart
	 * @param dayEnd
	 * @param resource
	 * @return dayEnd - dayStart in millis
	 * @throws CRPException if dayStart > dayEnd
	 */
	private Duration getAvailableDuration(final Instant dayStart, final Instant dayEnd, final Resource resource)
	{
		final Duration availableDuration = Duration.between(dayStart, dayEnd);
		if (availableDuration.isNegative())
		{
			throw new CRPException("@TimeSlotStart@ > @TimeSlotEnd@ (" + dayEnd + " > " + dayStart + ")")
					.setResource(resource);
		}
		return availableDuration;
	}

	private Instant scheduleForward(final Instant start, final Duration activityDuration, final Resource resource)
	{
		final ResourceType resourceType = getResourceType(resource);

		int iteration = 0; // statistical interation count
		Instant currentDate = start;
		Instant end = null;
		Duration remainingDuration = activityDuration;
		do
		{

			currentDate = reasoner.getAvailableDate(resource, currentDate, false);
			Instant dayStart = resourceType.getDayStart(currentDate);
			final Instant dayEnd = resourceType.getDayEnd(currentDate);

			// If working has already began at this day and the value is in the range of the
			// resource's availability, switch start time to the given again
			if (currentDate.isAfter(dayStart) && currentDate.isBefore(dayEnd))
			{
				dayStart = currentDate;
			}

			// The available time at this day in milliseconds
			final Duration availableDayDuration = getAvailableDuration(dayStart, dayEnd, resource);

			// The work can be finish on this day.
			if (availableDayDuration.compareTo(remainingDuration) >= 0)
			{
				end = dayStart.plus(remainingDuration);
				remainingDuration = Duration.ZERO;
				break;
			}
			// Otherwise recall with next day and the remained node duration.
			else
			{
				currentDate = currentDate
						.atZone(SystemTime.zoneId())
						.toLocalDate()
						.atTime(LocalTime.MAX)
						.plusDays(1)
						.atZone(SystemTime.zoneId())
						.toInstant();
				remainingDuration = remainingDuration.minus(availableDayDuration);
			}

			iteration++;
			if (iteration > p_MaxIterationsNo)
			{
				throw new CRPException("Maximum number of iterations exceeded (" + p_MaxIterationsNo + ")"
						+ " - Date:" + currentDate + ", RemainingMillis:" + remainingDuration);
			}
		}
		while (remainingDuration.toNanos() > 0);

		return end;
	}

	private ResourceType getResourceType(final Resource r)
	{
		final ResourceType resourceType = resourceProductService.getResourceTypeById(r.getResourceTypeId());
		return resourceType;
	}

	/**
	 * Calculate start date having duration and resource
	 */
	private Instant scheduleBackward(final Instant end, final Duration activityDuration, final Resource resource)
	{
		final ResourceType resourceType = getResourceType(resource);

		Instant start = null;
		Instant currentDate = end;
		Duration remainingDuration = activityDuration;
		int iteration = 0; // statistical iteration count
		do
		{
			log.info("--> end=" + currentDate);
			log.info("--> nodeDuration=" + remainingDuration);

			currentDate = reasoner.getAvailableDate(resource, currentDate, true);
			log.info("--> end(available)=" + currentDate);

			Instant dayEnd = resourceType.getDayEnd(currentDate);
			final Instant dayStart = resourceType.getDayStart(currentDate);

			log.info("--> dayStart=" + dayStart + ", dayEnd=" + dayEnd);

			// If working has already began at this day and the value is in the range of the
			// resource's availability, switch end time to the given again
			if (currentDate.isBefore(dayEnd) && currentDate.isAfter(dayStart))
			{
				dayEnd = currentDate;
			}

			// The available time at this day in milliseconds
			final Duration availableDayDuration = getAvailableDuration(dayStart, dayEnd, resource);

			// The work can be finish on this day.
			if (availableDayDuration.compareTo(remainingDuration) >= 0)
			{
				log.info("--> availableDayDuration >= nodeDuration true " + availableDayDuration + "|" + remainingDuration);
				start = dayEnd.minus(remainingDuration);
				remainingDuration = Duration.ZERO;
				break;
			}
			// Otherwise recall with previous day and the remained node duration.
			else
			{
				log.info("--> availableDayDuration >= nodeDuration false " + availableDayDuration + "|" + remainingDuration);
				log.info("--> nodeDuration-availableDayDuration " + remainingDuration.minus(availableDayDuration));

				currentDate = currentDate
						.atZone(SystemTime.zoneId())
						.toLocalDate()
						.atTime(LocalTime.MAX)
						.minusDays(1)
						.atZone(SystemTime.zoneId())
						.toInstant();

				remainingDuration = remainingDuration.minus(availableDayDuration);
			}
			//
			iteration++;
			if (iteration > p_MaxIterationsNo)
			{
				throw new CRPException("Maximum number of iterations exceeded (" + p_MaxIterationsNo + ")"
						+ " - Date:" + start + ", RemainingMillis:" + remainingDuration);
			}
		}
		while (remainingDuration.toNanos() > 0);

		log.info("         -->  start=" + start + " <---------------------------------------- ");
		return start;
	}
}
