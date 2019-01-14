/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/

package org.eevolution.form.crp;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.model.I_S_Resource;
import org.compiere.model.MProduct;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderRoutingActivitySchedule;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.reasoner.CRPReasoner;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.i18n.Msg;
import de.metas.material.planning.ResourceType;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 *
 * @author Teo Sarca, http://www.arhipac.ro
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class CRPDatasetFactory extends CRPReasoner implements CRPModel
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	// private final IResourceDAO resourcesRepo = Services.get(IResourceDAO.class);
	// private final IPPOrderDAO ordersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);

	private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	private JTree tree;
	private DefaultCategoryDataset dataset;

	public static CRPModel get(final LocalDateTime start, final LocalDateTime end, final ResourceId resourceId)
	{
		final CRPDatasetFactory factory = new CRPDatasetFactory();
		factory.generate(start, end, resourceId);

		return factory;
	}

	private void generate(final LocalDateTime start, final LocalDateTime end, final ResourceId resourceId)
	{
		if (start == null || end == null || resourceId == null)
		{
			return;
		}

		final ResourceType resourceType = getResourceType(resourceId);
		final TemporalUnit durationUnit = resourceType.getDurationUnit();

		final String rowKey_DailyCapacity = Msg.translate(Env.getCtx(), "DailyCapacity");
		final String rowKey_ActualLoad = Msg.translate(Env.getCtx(), "ActualLoad");
		final DateFormat formatter = DisplayType.getDateFormat(DisplayType.DateTime, Env.getLanguage(Env.getCtx()));

		final I_S_Resource resource = resourcesRepo.getById(resourceId);
		final Duration dailyCapacity = getDailyCapacity(resource);
		final BigDecimal dailyCapacityBD = DurationUtils.toBigDecimal(dailyCapacity, durationUnit);

		dataset = new DefaultCategoryDataset();
		final HashMap<DefaultMutableTreeNode, String> names = new HashMap<>();
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode(resource);
		names.put(root, getTreeNodeRepresentation(null, root, resource));

		LocalDateTime dateTime = start;
		while (end.isAfter(dateTime))
		{
			final String columnKey = formatter.format(TimeUtil.asDate(dateTime));

			names.putAll(addTreeNodes(dateTime, root, resource));

			if (isAvailable(resource, dateTime))
			{
				final BigDecimal actualLoadBD = BigDecimal.valueOf(calculateLoad(dateTime, resourceId).get(durationUnit));

				dataset.addValue(dailyCapacityBD, rowKey_DailyCapacity, columnKey);
				dataset.addValue(actualLoadBD, rowKey_ActualLoad, columnKey);
			}
			else
			{
				dataset.addValue(BigDecimal.ZERO, rowKey_DailyCapacity, columnKey);
				dataset.addValue(BigDecimal.ZERO, rowKey_ActualLoad, columnKey);
			}

			dateTime = dateTime.plusDays(1); // TODO: teo_sarca: increment should be more general, not only days
		}

		tree = new JTree(root);
		tree.setCellRenderer(new DiagramTreeCellRenderer(names));
	}

	@Override
	public Duration calculateLoad(final LocalDateTime dateTime, final ResourceId resourceId)
	{
		final ResourceType resourceType = getResourceType(resourceId);

		final BigDecimal qtyOpen;
		Duration duration = Duration.ZERO;

		for (final PPOrderRoutingActivitySchedule activity : orderRoutingsRepo.getActivitySchedulesByDateAndResource(dateTime, resourceId))
		{
			duration = duration.plus(calculateDurationForDay(dateTime, activity, resourceType));
		}

		return duration;
	}

	/**
	 * Gets {StartDate, EndDate} times for given dateTime.
	 * For calculating this, following factors are considered:
	 * <li>resource type time slot
	 * <li>node DateStartSchedule and DateEndSchedule
	 * 
	 * @param dateTime
	 * @param node
	 * @param resourceType resouce type
	 * @return array of 2 elements, {StartDate, EndDate}
	 */
	private static LocalDateTime[] getDayBorders(
			final LocalDateTime dateTime,
			final PPOrderRoutingActivitySchedule activity,
			final ResourceType resourceType)
	{

		// The theoretical latest time on a day, where the work ends, dependent on
		// the resource type's time slot value
		LocalDateTime endDayTime = resourceType.getDayEnd(dateTime);
		// Initialize the end time to the present, if the work ends at this day.
		// Otherwise the earliest possible start time for a day is set.
		endDayTime = endDayTime.isBefore(activity.getScheduledEndDate()) ? endDayTime : activity.getScheduledEndDate();

		// The theoretical earliest time on a day, where the work begins, dependent on
		// the resource type's time slot value
		LocalDateTime startDayTime = resourceType.getDayStart(dateTime);
		// Initialize the start time to the present, if the work begins at this day.
		// Otherwise the latest possible start time for a day is set.
		startDayTime = startDayTime.isAfter(activity.getScheduledStartDate()) ? startDayTime : activity.getScheduledStartDate();

		return new LocalDateTime[] { startDayTime, endDayTime };
	}

	private Duration calculateDurationForDay(final LocalDateTime dateTime, final PPOrderRoutingActivitySchedule activity, final ResourceType resourceType)
	{
		final LocalDateTime[] borders = getDayBorders(dateTime, activity, resourceType);
		return Duration.between(borders[0], borders[1]);
	}

	/**
	 * Generates following tree:
	 * 
	 * <pre>
	 * (dateTime)
	 *     \-------(root)
	 *     \-------(PP Order)
	 *                 \---------(PP Order Node)
	 * </pre>
	 * 
	 * @param dateTime
	 * @param root
	 * @param resource
	 * @return
	 */
	private Map<DefaultMutableTreeNode, String> addTreeNodes(final LocalDateTime dateTime, final DefaultMutableTreeNode root, final I_S_Resource resource)
	{
		final Map<DefaultMutableTreeNode, String> names = new HashMap<>();

		final DefaultMutableTreeNode parent = new DefaultMutableTreeNode(dateTime);
		names.put(parent, getTreeNodeRepresentation(null, parent, resource));
		root.add(parent);

		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());
		final List<PPOrderRoutingActivitySchedule> activities = orderRoutingsRepo.getActivitySchedulesByDateAndResource(dateTime, resourceId);
		if (activities.isEmpty())
		{
			return names;
		}

		final ImmutableListMultimap<PPOrderId, PPOrderRoutingActivitySchedule> activitiesByOrderId = Multimaps.index(activities, PPOrderRoutingActivitySchedule::getOrderId);

		for (final I_PP_Order order : ordersRepo.getByIds(activitiesByOrderId.keySet()))
		{
			final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());

			final DefaultMutableTreeNode childOrder = new DefaultMutableTreeNode(order);
			parent.add(childOrder);
			names.put(childOrder, getTreeNodeRepresentation(dateTime, childOrder, resource));

			for (final PPOrderRoutingActivitySchedule activity : activitiesByOrderId.get(orderId))
			{
				final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(activity);
				childOrder.add(childNode);
				names.put(childNode, getTreeNodeRepresentation(dateTime, childNode, resource));
			}
		}

		return names;
	}

	private String getTreeNodeRepresentation(final LocalDateTime dateTime, final DefaultMutableTreeNode node, final I_S_Resource resource)
	{
		final Object nodeData = node.getUserObject();

		String name = null;
		if (nodeData instanceof I_S_Resource)
		{
			final I_S_Resource res = (I_S_Resource)nodeData;
			name = res.getName();
		}
		else if (nodeData instanceof LocalDateTime)
		{
			final LocalDateTime d = (LocalDateTime)nodeData;
			final SimpleDateFormat df = Env.getLanguage(Env.getCtx()).getDateFormat();

			name = df.format(TimeUtil.asDate(d));
			if (!isAvailable(resource, d))
			{
				name = "{" + name + "}";
			}
		}
		else if (nodeData instanceof I_PP_Order)
		{
			final I_PP_Order order = (I_PP_Order)nodeData;
			final ProductId mainProductId = ProductId.ofRepoId(order.getM_Product_ID());
			final MProduct p = MProduct.get(Env.getCtx(), order.getM_Product_ID());
			final String productName = productBL.getProductName(mainProductId);

			name = order.getDocumentNo() + " (" + productName + ")";
		}
		else if (nodeData instanceof PPOrderRoutingActivitySchedule)
		{
			final PPOrderRoutingActivitySchedule activity = (PPOrderRoutingActivitySchedule)nodeData;
			final ResourceType resourceType = getResourceType(resource);

			final LocalDateTime[] interval = getDayBorders(dateTime, activity, resourceType);
			name = interval[0].format(timeFormatter)
					+ " - "
					+ interval[1].format(timeFormatter)
			// + " " + activity.getName()
			// + " (" + orderWorkflow.getName() + ")"
			;
		}

		return name;
	}

	/**
	 * @return Daily Capacity * Utilization / 100
	 */
	private Duration getDailyCapacity(final I_S_Resource resource)
	{
		final TemporalUnit durationUnit = getResourceType(resource).getDurationUnit();

		final Duration dailyCapacity = Duration.of(resource.getDailyCapacity().intValue(), durationUnit);

		return dailyCapacity
				.multipliedBy(resource.getPercentUtilization().intValue())
				.dividedBy(100);
	}

	@Override
	public CategoryDataset getDataset()
	{
		return dataset;
	}

	@Override
	public JTree getTree()
	{
		return tree;
	}
}
