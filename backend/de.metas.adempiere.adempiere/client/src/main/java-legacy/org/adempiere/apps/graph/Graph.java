/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.adempiere.apps.graph;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;

import org.compiere.apps.AEnv;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.MGoal;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MQuery;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * 	Graph
 *
 *  @author Jorg Janke
 *  @version $Id: BarGraph.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2507325 ] BarGraph zoom not working
 * @author hengsin
 *          <li> Add support for other type of graph
 */
public class Graph extends CPanel implements ChartMouseListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -4150122585550132822L;

	/**
	 * 	Constructor
	 */
	public Graph()
	{
		super();
		this.setLayout(new BorderLayout());
		builder = new GraphBuilder();
	}	//	BarGraph

	/**
	 * 	Constructor
	 *	@param goal goal
	 */
	public Graph(MGoal goal)
	{
		this(goal, false);
	}

	/**
	 * 	Constructor
	 *	@param goal goal
	 */
	public Graph(MGoal goal, boolean userSelection)
	{
		this();
		builder = new GraphBuilder();
		builder.setMGoal(goal);
		builder.setYAxisLabel(goal.getName());
		builder.setXAxisLabel(goal.getXAxisText());
		m_userSelection = userSelection;
		loadData();
		//addComponentListener(this);
	}	//	BarGraph


	/** Graph Size				*/
	//private Dimension 		m_size = null;
	/** Zero/Zero Coordinate point	*/
	private Point			m_point0_0 = null;
//	/** Layout					*/
//	private BarGraphLayout	m_layout = new BarGraphLayout(this);

	/**	Logger					*/
	private static Logger log = LogManager.getLogger(Graph.class);


	/** Y Axis Target Line		*/
	private double		m_Y_Target	= 0;
	/** Y Axis Target Line Label */
	private String		m_Y_TargetLabel = null;
	private static Dimension			paneldimension = new Dimension(180, 150);

	private GraphBuilder builder;

	/**
	 * 	Load Performance Data
	 */
	ArrayList<GraphColumn> list = new ArrayList<GraphColumn>();
	private boolean m_userSelection;


	private void loadData()
	{

		list = builder.loadData();
		JFreeChart chart = builder.createChart(builder.getMGoal().getChartType());
		if (chartPanel != null)
			remove(chartPanel);

		chartPanel = new ChartPanel(chart);
		chartPanel.setSize(getSize());
		chartPanel.addChartMouseListener(this);
		add(chartPanel,BorderLayout.CENTER);

		if (m_userSelection)
		{
			final int adColumnId = -1;
			final int AD_Reference_Value_ID = DB.getSQLValue(null, "SELECT AD_Reference_ID FROM AD_Reference WHERE Name = ?", "PA_Goal ChartType");
			MLookupInfo info = MLookupFactory.getLookup_List(AD_Reference_Value_ID);
			MLookup mLookup = new MLookup(Env.getCtx(), adColumnId, info, Env.TAB_None);
			VLookup lookup = new VLookup("ChartType", false, false, true,
					mLookup);
			lookup.addVetoableChangeListener(new VetoableChangeListener() {

				@Override
				public void vetoableChange(PropertyChangeEvent evt)
						throws PropertyVetoException {
					Object value = evt.getNewValue();
					if (value == null) return;
					JFreeChart chart = null;
					chart = builder.createChart(value.toString());

					if (chart != null)
					{
						if (chartPanel != null)
							remove(chartPanel);

						chartPanel = new ChartPanel(chart);
						chartPanel.setSize(getSize());
						chartPanel.addChartMouseListener(Graph.this);
						add(chartPanel,BorderLayout.CENTER);
						getParent().validate();

					}
				}

			});
			add(lookup, BorderLayout.NORTH);
		}
		this.setMinimumSize(paneldimension);
	}	//	loadData

	private ChartPanel chartPanel;
	/**
	 * 	Get Point 0_0
	 *	@return point
	 */
	public Point getPoint0_0()
	{
		return m_point0_0;
	}	//	getPoint0_0


	/**
	 * @return Returns the x_AxisLabel.
	 */
	public String getX_AxisLabel ()
	{
		return builder.getXAxisLabel();
	}	//	getX_AxisLabel

	/**
	 * @param axisLabel The x_AxisLabel to set.
	 */
	public void setX_AxisLabel (String axisLabel)
	{
		builder.setXAxisLabel(axisLabel);
	}	//	setX_AxisLabel

	/**
	 * @return Returns the y_AxisLabel.
	 */
	public String getY_AxisLabel ()
	{
		return builder.getYAxisLabel();
	}	//	getY_AxisLabel

	/**
	 * @param axisLabel The y_AxisLabel to set.
	 */
	public void setY_AxisLabel (String axisLabel)
	{
		builder.setYAxisLabel(axisLabel);
	}	//	setY_AxisLabel

	/**
	 * @return Returns the y_TargetLabel.
	 */
	public String getY_TargetLabel ()
	{
		return m_Y_TargetLabel;
	}	//	getY_TargetLabel

	/**
	 * @param targetLabel The y_TargetLabel to set.
	 */
	public void setY_TargetLabel (String targetLabel, double target)
	{
		m_Y_TargetLabel = targetLabel;
		m_Y_Target = target;
	}	//	setY_TargetLabel


	/**
	 * Get BarGraphColumn for ChartEntity
	 * @param event
	 * @return BarGraphColumn or null if not found
	 */
	private GraphColumn getGraphColumn(ChartMouseEvent event)
	{
		ChartEntity entity = event.getEntity();
		String key = null;
		if (entity instanceof CategoryItemEntity)
		{
			Comparable<?> colKey = ((CategoryItemEntity)entity).getColumnKey();
			if (colKey != null)
			{
				key = colKey.toString();
			}
		}
		else if (entity instanceof PieSectionEntity)
		{
			Comparable<?> sectionKey = ((PieSectionEntity)entity).getSectionKey();
			if (sectionKey != null)
			{
				key = sectionKey.toString();
			}
		}
		if (key == null)
		{
			return null;
		}
		for (int i = 0; i < list.size(); i++)
		{
			final String label = list.get(i).getLabel();
			if (key.equals(label))
			{
				return list.get(i);
			}
		}
		//
		return null;
	}

	@Override
	public void chartMouseClicked(ChartMouseEvent event)
	{
		if ((event.getEntity()!=null) && (event.getTrigger().getClickCount() > 1))
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try
			{
				GraphColumn bgc = getGraphColumn(event);
				if (bgc == null)
				{
					return;
				}

				MQuery query = bgc.getMQuery(builder.getMGoal());
				if (query != null)
					AEnv.zoom(query);
				else
					log.warn("Nothing to zoom to - " + bgc);
			}
			finally
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event)
	{
	}

	public GraphColumn[] getGraphColumnList()
	{
		return list.toArray(new GraphColumn[list.size()]);
	}
}	//	BarGraph
