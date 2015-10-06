/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.apps.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;

import org.compiere.model.MGoal;
import org.compiere.model.MMeasure;
import org.compiere.model.X_PA_Goal;
import org.compiere.util.CLogger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author hengsin
 *
 */
public class GraphBuilder {

	/** The Goal				*/
	protected MGoal 		m_goal = null;
	/** X Axis Label			*/
	protected String		m_X_AxisLabel = "X Axis";
	/** Y Axis Label			*/
	protected String		m_Y_AxisLabel = "Y Axis";
	protected DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	protected DefaultCategoryDataset linearDataset = new DefaultCategoryDataset();
	protected DefaultPieDataset pieDataset = new DefaultPieDataset();

	private static final CLogger log = CLogger.getCLogger(GraphBuilder.class);

	public GraphBuilder() {
		super();
	}

	/**
	 *
	 * @param type
	 * @return JFreeChart
	 */
	public JFreeChart createChart(String type) {
		if(X_PA_Goal.CHARTTYPE_BarChart.equals(type))
		{
			return createBarChart();
		}
		else if (X_PA_Goal.CHARTTYPE_PieChart.equals(type))
		{
			return createPieChart();
		}
		else if (X_PA_Goal.CHARTTYPE_AreaChart.equals(type))
		{
			return createAreaChart();
		}
		else if (X_PA_Goal.CHARTTYPE_LineChart.equals(type))
		{
			return createLineChart();
		}
		else if (X_PA_Goal.CHARTTYPE_RingChart.equals(type))
		{
			return createRingChart();
		}
		else if (X_PA_Goal.CHARTTYPE_WaterfallChart.equals(type))
		{
			return createWaterfallChart();
		}
		else
		{
			throw new IllegalArgumentException("unknown chart type=" + type);
		}
	}

	private JFreeChart createWaterfallChart() {
		JFreeChart chart = ChartFactory.createWaterfallChart(
				m_goal.getMeasure().getName(),         // chart title
				m_X_AxisLabel,               // domain axis label
				m_Y_AxisLabel,                  // range axis label
				linearDataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				false,                     // include legend
				true,                     // tooltips?
				true                     // URLs?
		);

		setupCategoryChart(chart);
		return chart;
	}

	private JFreeChart createRingChart() {
		final JFreeChart chart = ChartFactory.createRingChart(m_goal.getMeasure().getName(),
				pieDataset, false, true, true);

		return chart;
	}

	private JFreeChart createBarChart() {
		JFreeChart chart = ChartFactory.createBarChart3D(
				m_goal.getMeasure().getName(),         // chart title
				m_X_AxisLabel,               // domain axis label
				m_Y_AxisLabel,                  // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				false,                     // include legend
				true,                     // tooltips?
				true                     // URLs?
		);

		setupCategoryChart(chart);
		return chart;
	}

	private JFreeChart createPieChart() {
		final JFreeChart chart = ChartFactory.createPieChart3D(m_goal.getMeasure().getName(),
				pieDataset, false, true, true);

		return chart;
	}

	private JFreeChart createAreaChart() {
		// create the chart...
		JFreeChart chart = ChartFactory.createAreaChart(
				m_goal.getMeasure().getName(),         // chart title
				m_X_AxisLabel,               // domain axis label
				m_Y_AxisLabel,                  // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				false,                     // include legend
				true,                     // tooltips?
				true                     // URLs?
		);

		setupCategoryChart(chart);
		return chart;
	}

	private void setupCategoryChart(JFreeChart chart) {
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(92/255f, 178/255f, 232/255f));
		renderer.setSeriesPaint(1, new Color(56/255f, 97/255f, 119/255f));
		renderer.setSeriesPaint(2, new Color(242/255f, 70/255f, 78/255f));
		renderer.setSeriesPaint(3, Color.orange);
		renderer.setSeriesPaint(4, new Color(147/255f, 196/255f, 51/255f));
		renderer.setSeriesPaint(5, new Color(210/255f, 247/255f, 91/255f));
		renderer.setSeriesPaint(6, new Color(129/255f, 235/255f, 249/255f));
		renderer.setSeriesPaint(7, new Color(60/255f, 84/255f, 8/255f));
		renderer.setSeriesPaint(8, new Color(0.8f, 0.8f, 0.8f));
	}

	private JFreeChart createLineChart() {
		// create the chart...
		JFreeChart chart = ChartFactory.createLineChart3D(
				m_goal.getMeasure().getName(),         // chart title
				m_X_AxisLabel,               // domain axis label
				m_Y_AxisLabel,                  // range axis label
				linearDataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				false,                     // include legend
				true,                     // tooltips?
				true                     // URLs?
		);

		setupCategoryChart(chart);
		return chart;
	}

	/**
	 *
	 * @return MGoal
	 */
	public MGoal getMGoal() {
		return m_goal;
	}

	/**
	 *
	 * @param mgoal
	 */
	public void setMGoal(MGoal mgoal) {
		m_goal = mgoal;
	}

	/**
	 *
	 * @return X axis label
	 */
	public String getXAxisLabel() {
		return m_X_AxisLabel;
	}

	/**
	 *
	 * @param axisLabel
	 */
	public void setXAxisLabel(String axisLabel) {
		m_X_AxisLabel = axisLabel;
	}

	/**
	 *
	 * @return Y axis label
	 */
	public String getYAxisLabel() {
		return m_Y_AxisLabel;
	}

	/**
	 *
	 * @param axisLabel
	 */
	public void setYAxisLabel(String axisLabel) {
		m_Y_AxisLabel = axisLabel;
	}

	/**
	 *
	 * @return graph column list
	 */
	public ArrayList<GraphColumn> loadData() {
		//	Calculated
		MMeasure measure = getMGoal().getMeasure();
		if (measure == null)
		{
			log.warning("No Measure for " + getMGoal());
			return null;
		}

		ArrayList<GraphColumn>list = measure.getGraphColumnList(getMGoal());

		pieDataset = new DefaultPieDataset();
		dataset = new DefaultCategoryDataset();
		for (int i = 0; i < list.size(); i++){
			String series = m_X_AxisLabel;
			if (list.get(i).getDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(list.get(i).getDate());
				series = Integer.toString(cal.get(Calendar.YEAR));
			}
			dataset.addValue(list.get(i).getValue(), series,
					list.get(i).getLabel());
			linearDataset.addValue(list.get(i).getValue(), m_X_AxisLabel,
					list.get(i).getLabel());
			pieDataset.setValue(list.get(i).getLabel(), list.get(i).getValue());
		}
		return list;
	}
}