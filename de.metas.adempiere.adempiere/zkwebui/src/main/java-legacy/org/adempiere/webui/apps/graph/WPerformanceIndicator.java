/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *
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
package org.adempiere.webui.apps.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import org.adempiere.webui.component.Panel;
import org.compiere.model.MColorSchema;
import org.compiere.model.MGoal;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.RectangleInsets;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Image;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 * 	Performance Indicator
 *
 *  @author hengsin
 */
public class WPerformanceIndicator extends Panel implements EventListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3580494126343850939L;

	/**
	 * 	Constructor
	 *	@param goal goal model
	 */
	public WPerformanceIndicator(MGoal goal)
	{
		super();

		m_goal = goal;

		init();
		mRefresh.addEventListener(Events.ON_CLICK, this);
		popupMenu.appendChild(mRefresh);

		addEventListener(Events.ON_DOUBLE_CLICK, this);
		addEventListener(Events.ON_CLICK, this);
	}	//	PerformanceIndicator

	private MGoal				m_goal = null;
	/**	The Performance Name		*/
	private String				m_text = null;
	/** Performance Line			*/

	/** Integer Number Format		*/
	private static DecimalFormat	s_format = DisplayType.getNumberFormat(DisplayType.Integer);

	Menupopup 					popupMenu = new Menupopup();
	private Menuitem 			mRefresh = new Menuitem(Msg.getMsg(Env.getCtx(), "Refresh"), "/images/Refresh16.png");

	ChartPanel chartPanel;

	/**
	 * 	Get Goal
	 *	@return goal
	 */
	public MGoal getGoal()
	{
		return m_goal;
	}	//	getGoal

	private  JFreeChart createChart()
	{
		JFreeChart chart = null;

		//	Set Text
		StringBuffer text = new StringBuffer(m_goal.getName());
		if (m_goal.isTarget())
			text.append(": ").append(m_goal.getPercent()).append("%");
		else
			text.append(": ").append(s_format.format(m_goal.getMeasureActual()));

		m_text = text.toString();

		//	ToolTip
		text = new StringBuffer();
		if (m_goal.getDescription() != null)
			text.append(m_goal.getDescription()).append(": ");
		text.append(s_format.format(m_goal.getMeasureActual()));
		if (m_goal.isTarget())
			text.append(" ").append(Msg.getMsg(Env.getCtx(), "of")).append(" ")
				.append(s_format.format(m_goal.getMeasureTarget()));
		setTooltiptext(text.toString());
		//
        DefaultValueDataset data = new DefaultValueDataset((float)m_goal.getPercent());
        MeterPlot plot = new MeterPlot(data);

        MColorSchema colorSchema = m_goal.getColorSchema();
        int rangeLo = 0; int rangeHi=0;
        for (int i=1; i<=4; i++){
            switch (i) {
             case 1: rangeHi = colorSchema.getMark1Percent(); break;
             case 2: rangeHi = colorSchema.getMark2Percent(); break;
             case 3: rangeHi = colorSchema.getMark3Percent(); break;
             case 4: rangeHi = colorSchema.getMark4Percent(); break;
            }
            if (rangeHi==9999)
            	rangeHi = (int) Math.floor(rangeLo*1.5);
            if (rangeLo < rangeHi) {
            	plot.addInterval(new MeterInterval("Normal", //label
                 	  new Range(rangeLo, rangeHi), //range
                 	  colorSchema.getColor(rangeHi),
                 	  new BasicStroke(7.0f),
                 	  new Color(-13091716)
                ));
            	rangeLo = rangeHi;
            }
        }
        plot.setRange(new Range(0,rangeLo));

        plot.setDialBackgroundPaint(new Color(-13091716));
        plot.setUnits("");
        plot.setDialShape(DialShape.CHORD);//CIRCLE);
        plot.setNeedlePaint(Color.white);
        plot.setTickSize(2000);
        plot.setTickLabelFont(new Font("SansSerif", Font.BOLD, 8));
        plot.setValueFont(new Font("SansSerif", Font.BOLD, 8));
        plot.setNoDataMessageFont(new Font("SansSerif", Font.BOLD, 8));
        plot.setTickLabelPaint(Color.white);
        plot.setInsets(new RectangleInsets(1.0, 2.0, 3.0, 4.0));

        chart = new JFreeChart( m_text, new Font("SansSerif", Font.BOLD, 9), plot,false);

		return chart;
	}

     /**
	 * 	Init Graph Display
	 *  Kinamo (pelgrim)
	 */
	private void init()
	{
		JFreeChart chart = createChart();
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderVisible(true);
		chart.setBorderPaint(Color.LIGHT_GRAY);
		chart.setAntiAlias(true);
		BufferedImage bi = chart.createBufferedImage(200, 120, BufferedImage.TRANSLUCENT , null);
		try {
		    byte[] bytes = EncoderUtil.encode(bi, ImageFormat.PNG, true);

		    AImage image = new AImage("", bytes);
		    Image myImage = new Image();
		    myImage.setContent(image);
		    appendChild(myImage);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}

	    invalidate();
	}


	/**
	 * 	Update Display Data
	 */
	protected void updateDisplay()
	{
		chartPanel.setChart(createChart());
	    invalidate();
	}	//	updateData

	public void onEvent(Event event) throws Exception
	{
	}
}
