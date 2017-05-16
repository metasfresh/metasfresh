/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.util.Services;
import org.compiere.model.MProduct;
import org.compiere.model.MResource;
import org.compiere.model.MResourceType;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPOrderNode;
import org.eevolution.model.MPPOrderWorkflow;
import org.eevolution.model.reasoner.CRPReasoner;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.metas.i18n.Msg;
import de.metas.material.planning.IResourceProductService;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 * 
 * @author Teo Sarca, http://www.arhipac.ro
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public abstract class CRPDatasetFactory extends CRPReasoner implements CRPModel
{
	protected JTree tree;
	protected DefaultCategoryDataset dataset;
	
	/**
	 * Convert from minutes to base UOM
	 */
	protected abstract BigDecimal convert(BigDecimal minutes);
	
	public static CRPModel get(Timestamp start, Timestamp end, MResource r)
	{
		MResourceType t = MResourceType.get(Env.getCtx(), r.getS_ResourceType_ID());
		// UOM ID - 'Minutes' is base unit
		final MUOM uom1 = MUOM.get(Env.getCtx(), MUOM.getMinute_UOM_ID(Env.getCtx()));
		// Target UOM is the resource type's UOM
		final MUOM uom2 = MUOM.get(Env.getCtx(), t.getC_UOM_ID());

		CRPDatasetFactory factory = new CRPDatasetFactory() {
			protected BigDecimal convert(BigDecimal minutes)
			{
				return MUOMConversion.convert(Env.getCtx(), uom1.get_ID(), uom2.get_ID(), minutes);
			}
		};
		factory.generate(start, end, r);

		return factory;
	}

	private void generate(Timestamp start, Timestamp end, MResource r)
	{
		 if(start == null || end == null || r == null)
		 {
			 return ;
		 }
			  	
		 String labelActCap = Msg.translate(Env.getCtx(), "DailyCapacity");
		 String labelLoadAct = Msg.translate(Env.getCtx(), "ActualLoad");
	 	 DateFormat formatter = DisplayType.getDateFormat(DisplayType.DateTime, Env.getLanguage(Env.getCtx()));
		 
		 BigDecimal dailyCapacity = getMaxRange(r);

	 	 dataset = new DefaultCategoryDataset();
	 	 HashMap<DefaultMutableTreeNode, String> names = new HashMap<DefaultMutableTreeNode, String>();
	 	 DefaultMutableTreeNode root = new DefaultMutableTreeNode(r);
	 	 names.put(root, getTreeNodeRepresentation(null, root, r));

	 	 Timestamp dateTime = start;
	 	 while(end.after(dateTime))
	 	 {
	 		 String label = formatter.format(dateTime);
	 		 names.putAll(addTreeNodes(dateTime, root, r));

	 		 boolean available = isAvailable(r, dateTime);
	 		 dataset.addValue(available ? dailyCapacity : BigDecimal.ZERO, labelActCap, label);
	 		 dataset.addValue(available ? calculateLoad(dateTime, r, null) : BigDecimal.ZERO, labelLoadAct, label);

	 		 dateTime = org.compiere.util.TimeUtil.addDays(dateTime, 1); // TODO: teo_sarca: increment should be more general, not only days
	 	 } 	 	

	 	 tree = new JTree(root);
	 	 tree.setCellRenderer(new DiagramTreeCellRenderer(names));
	}

	public BigDecimal calculateLoad(Timestamp dateTime, MResource r, String docStatus)
	{
		MResourceType t = MResourceType.get(Env.getCtx(), r.getS_ResourceType_ID());
		MUOM uom = MUOM.get(Env.getCtx(), t.getC_UOM_ID());

		BigDecimal qtyOpen;
		long millis = 0l;
		for(MPPOrderNode node : getPPOrderNodes(dateTime, r))
		{                        
			if (docStatus != null)
			{
				MPPOrder o = new MPPOrder(node.getCtx(), node.getPP_Order_ID(), node.get_TrxName());
				if(!o.getDocStatus().equals(docStatus))
				{
					continue;
				}
			}
			millis += calculateMillisForDay(dateTime, node, t);
		}

		// Pre-converts to minutes, because its the lowest time unit of compiere 
		BigDecimal scale = new BigDecimal(1000*60);
		BigDecimal minutes = new BigDecimal(millis).divide(scale, 2, BigDecimal.ROUND_HALF_UP);
		return convert(minutes);
	}
	
	/**
	 * Gets {StartDate, EndDate} times for given dateTime.
	 * For calculating this, following factors are considered:
	 * <li> resource type time slot
	 * <li> node DateStartSchedule and DateEndSchedule 
	 * @param dateTime
	 * @param node
	 * @param t resouce type
	 * @return array of 2 elements, {StartDate, EndDate}   
	 */
	private Timestamp[] getDayBorders(Timestamp dateTime, MPPOrderNode node, MResourceType t)
	{
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
		
 		// The theoretical latest time on a day, where the work ends, dependent on
		// the resource type's time slot value
		Timestamp endDayTime = resourceProductService.getDayEndForResourceType(t, dateTime);
		// Initialize the end time to the present, if the work ends at this day.
		// Otherwise the earliest possible start time for a day is set.
		endDayTime = (endDayTime.before(node.getDateFinishSchedule())) ? endDayTime : node.getDateFinishSchedule();
		
 		// The theoretical earliest time on a day, where the work begins, dependent on
		// the resource type's time slot value
		Timestamp startDayTime = resourceProductService.getDayStartForResourceType(t, dateTime);
		// Initialize the start time to the present, if the work begins at this day.
		// Otherwise the latest possible start time for a day is set.
		startDayTime = (startDayTime.after(node.getDateStartSchedule())) ? startDayTime : node.getDateStartSchedule();

		return new Timestamp[] {startDayTime, endDayTime};
	}
	
	private long calculateMillisForDay(Timestamp dateTime, MPPOrderNode node, MResourceType t)
	{
		Timestamp[] borders = getDayBorders(dateTime, node, t);
		return borders[1].getTime() - borders[0].getTime();
	}

	/**
	 * Generates following tree:
	 * <pre>
	 * (dateTime)
	 *     \-------(root)
	 *     \-------(PP Order)
	 *                 \---------(PP Order Node)
	 * </pre>
	 * @param dateTime
	 * @param root
	 * @param r
	 * @return
	 */
	private HashMap<DefaultMutableTreeNode, String> addTreeNodes(Timestamp dateTime, DefaultMutableTreeNode root, MResource r)
	{
		HashMap<DefaultMutableTreeNode, String> names = new HashMap<DefaultMutableTreeNode, String>();
		
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(dateTime);
		names.put(parent, getTreeNodeRepresentation(null, parent, r));
		root.add(parent);

		for(MPPOrder order : getPPOrders(dateTime, r))
		{		
			DefaultMutableTreeNode childOrder = new DefaultMutableTreeNode(order);
			parent.add(childOrder);
			names.put(childOrder, getTreeNodeRepresentation(dateTime, childOrder, r));
			
			for(MPPOrderNode node : getPPOrderNodes(dateTime, r))
			{
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(node);
				childOrder.add(childNode);
				names.put(childNode, getTreeNodeRepresentation(dateTime, childNode, r));
			}
		}
		
		return names;
	}

    private String getTreeNodeRepresentation(Timestamp dateTime, DefaultMutableTreeNode node, MResource r)
    {
        String name = null;
        if(node.getUserObject() instanceof MResource)
        {
        	MResource res = (MResource) node.getUserObject();
        	name = res.getName();
        }
        else if(node.getUserObject() instanceof Timestamp)
        {
        	Timestamp d = (Timestamp)node.getUserObject();
    		SimpleDateFormat df = Env.getLanguage(Env.getCtx()).getDateFormat();

        	name = df.format(d);
       		if(!isAvailable(r, d))
       		{
       			name = "{"+name+"}";
       		}
        }
        else if(node.getUserObject() instanceof MPPOrder)
        {
        	MPPOrder o = (MPPOrder)node.getUserObject();
        	MProduct p = MProduct.get(Env.getCtx(), o.getM_Product_ID());
        	
        	name = o.getDocumentNo()+" ("+p.getName()+")";
        }
        else if(node.getUserObject() instanceof MPPOrderNode)
        {
        	MPPOrderNode on = (MPPOrderNode)node.getUserObject();
        	MPPOrderWorkflow owf = on.getMPPOrderWorkflow();
        	MResourceType rt = MResourceType.get(Env.getCtx(), r.getS_ResourceType_ID());

        	// no function
        	//Env.getLanguage(Env.getCtx()).getTimeFormat();
        	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        	Timestamp[] interval = getDayBorders(dateTime, on, rt);
       		name = df.format(interval[0])+" - "+df.format(interval[1])+" "+on.getName()+" ("+owf.getName()+")";
       	}
        
        return name;
    }
    
    /**
     * @return Daily Capacity * Utilization / 100
     */
    private BigDecimal getMaxRange(MResource r)
    {
    	BigDecimal utilizationDec = r.getPercentUtilization().divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
    	int precision = 2; // TODO: hardcoded
    	return r.getDailyCapacity().multiply(utilizationDec).setScale(precision, RoundingMode.HALF_UP);
    }
    
    public CategoryDataset getDataset()
    {
    	return dataset;
    }

	public JTree getTree()
	{
		return tree;
	}
}
