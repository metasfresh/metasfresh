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

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.adempiere.apps.graph.GraphBuilder;
import org.adempiere.apps.graph.GraphColumn;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.model.MGoal;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.zkoss.image.AImage;
import org.zkoss.zhtml.A;
import org.zkoss.zhtml.Br;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Text;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.East;
import org.zkoss.zul.Area;
import org.zkoss.zul.Div;
import org.zkoss.zul.Imagemap;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Toolbar;

/**
 * Performance Graph
 * 
 * @author hengsin
 */
public class WGraph extends Div implements IdSpace {
	/**
	 *
	 */
	private static final long serialVersionUID = -975989183542113080L;

	private static final String ZOOM_KEY = "queryZoom";

	private boolean m_hideTitle;

	private Panel panel;

	private boolean m_renderTable = false;

	private boolean m_renderChart = true;

	/** Zero/Zero Coordinate point */
	private Point m_point0_0 = null;

	/** Logger */
	private static CLogger log = CLogger.getCLogger(WGraph.class);

	/** Y Axis Target Line Label */
	private String m_Y_TargetLabel = null;

	/**
	 * Load Performance Data
	 */
	ArrayList<GraphColumn> list = new ArrayList<GraphColumn>();

	private GraphBuilder builder;

	private boolean m_chartSelection;

	private int zoomFactor = 0;

	/**
	 * Constructor
	 */
	public WGraph() {
		super();
		builder = new GraphBuilder();		
		panel = new Panel();
	} // BarGraph

	/**
	 * Constructor
	 * 
	 * @param goal
	 */
	public WGraph(MGoal goal) {
		this(goal, 0, false, false, false, true);
	}

	/**
	 * Constructor
	 * 
	 * @param goal
	 * @param zoom
	 * @param userSelection
	 * @param hideTitle
	 * @param showTable
	 * @param showChart
	 */
	public WGraph(MGoal goal, int zoom, boolean chartSelection,
			boolean hideTitle, boolean renderTable, boolean renderChart) {
		this();
		setGoal(goal);
		m_chartSelection = chartSelection;
		zoomFactor = zoom;
		m_hideTitle = hideTitle;
		m_renderTable = renderTable;
		m_renderChart = renderChart;
		
		loadData();
				
		render();
	} // WGraph

	/**
	 * @param goal
	 */
	public void setGoal(MGoal goal)
	{
		builder.setMGoal(goal);
		builder.setYAxisLabel(goal.getName());
		builder.setXAxisLabel(goal.getXAxisText());
	}

	/**
	 * @return true if the chart type selection control is available 
	 */
	public boolean isChartSelection() {
		return m_chartSelection;
	}

	/**
	 * show/hide the chart type selection control
	 * @param chartSelection
	 */
	public void setChartSelection(boolean chartSelection) {
		m_chartSelection = chartSelection;
	}
	
	/**
	 * render chart and/or table
	 */
	public void render() {
		Borderlayout layout = null;
		
		this.getChildren().clear();
		
		if (m_renderTable && m_renderChart) {
			layout = new Borderlayout();
			appendChild(layout);
			layout.setStyle("height: 100%; width: 100%; position: absolute;");
			Center center = new Center();
			layout.appendChild(center);
			center.appendChild(panel);
		} else {			
			appendChild(panel);
		}
		
		if (m_renderChart) {
			JFreeChart chart = builder.createChart(builder.getMGoal()
					.getChartType());

			render(chart);
		}
		if (m_renderTable) {
			if (m_renderChart) {
				East east = new East();
				layout.appendChild(east);
				renderTable(east);
			} else {
				Panelchildren pc = panel.getPanelchildren();
				if (pc == null) {
					pc = new Panelchildren();
					panel.appendChild(pc);
				} else {
					pc.getChildren().clear();
				}
				renderTable(pc);
			}
		}
	}
	
	private void loadData() {
		list = builder.loadData();

		if (m_renderChart && m_chartSelection) {
			Toolbar toolbar = new Toolbar();
			panel.appendChild(toolbar);

			int AD_Reference_Value_ID = DB.getSQLValue(null,
					"SELECT AD_Reference_ID FROM AD_Reference WHERE Name = ?",
					"PA_Goal ChartType");
			MLookupInfo info = MLookupFactory.getLookup_List(Env
					.getLanguage(Env.getCtx()), AD_Reference_Value_ID);
			MLookup mLookup = new MLookup(info, 0);
			WTableDirEditor editor = new WTableDirEditor("ChartType", false,
					false, true, mLookup);
			toolbar.appendChild(editor.getComponent());
			editor.addValueChangeListener(new ValueChangeListener() {

				public void valueChange(ValueChangeEvent evt) {
					Object value = evt.getNewValue();
					if (value == null || value.toString().trim().length() == 0)
						return;
					JFreeChart chart = null;
					chart = builder.createChart(value.toString());
					if (chart != null)
						render(chart);
				}

			});
		}
	} // loadData

	private void render(JFreeChart chart) {
		ChartRenderingInfo info = new ChartRenderingInfo();
		int width = 560;
		int height = 400;
		if (zoomFactor > 0) {
			width = width * zoomFactor / 100;
			height = height * zoomFactor / 100;
		}
		if (m_hideTitle) {
			chart.setTitle("");
		}
		BufferedImage bi = chart.createBufferedImage(width, height,
				BufferedImage.TRANSLUCENT, info);
		try {
			byte[] bytes = EncoderUtil.encode(bi, ImageFormat.PNG, true);

			AImage image = new AImage("", bytes);
			Imagemap myImage = new Imagemap();

			myImage.setContent(image);
			if (panel.getPanelchildren() != null) {
				panel.getPanelchildren().getChildren().clear();
				panel.getPanelchildren().appendChild(myImage);
			} else {
				Panelchildren pc = new Panelchildren();
				panel.appendChild(pc);
				pc.appendChild(myImage);
			}

			int count = 0;
			for (Iterator<?> it = info.getEntityCollection().getEntities()
					.iterator(); it.hasNext();) {
				ChartEntity entity = (ChartEntity) it.next();

				String key = null;
				if (entity instanceof CategoryItemEntity) {
					Comparable<?> colKey = ((CategoryItemEntity) entity)
							.getColumnKey();
					if (colKey != null) {
						key = colKey.toString();
					}
				} else if (entity instanceof PieSectionEntity) {
					Comparable<?> sectionKey = ((PieSectionEntity) entity)
							.getSectionKey();
					if (sectionKey != null) {
						key = sectionKey.toString();
					}
				}
				if (key == null) {
					continue;
				}

				Area area = new Area();
				myImage.appendChild(area);
				area.setCoords(entity.getShapeCoords());
				area.setShape(entity.getShapeType());
				area.setTooltiptext(entity.getToolTipText());
				area.setId(count+"_WG_" + key);
				count++;
			}

			myImage.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event event) throws Exception {
					MouseEvent me = (MouseEvent) event;
					String areaId = me.getArea();
					if (areaId != null) {
						for (int i = 0; i < list.size(); i++) {
							String s = "_WG_" + list.get(i).getLabel();
							if (areaId.endsWith(s)) {
								chartMouseClicked(i);
								return;
							}
						}
					}
				}
			});
		} catch (Exception e) {
			log.log(Level.SEVERE, "", e);
		}
	}

	/**
	 * Get Point 0_0
	 * 
	 * @return point
	 */
	public Point getPoint0_0() {
		return m_point0_0;
	} // getPoint0_0

	/**
	 * @return Returns the x_AxisLabel.
	 */
	public String getX_AxisLabel() {
		return builder.getXAxisLabel();
	} // getX_AxisLabel

	/**
	 * @param axisLabel
	 *            The x_AxisLabel to set.
	 */
	public void setX_AxisLabel(String axisLabel) {
		builder.setXAxisLabel(axisLabel);
	} // setX_AxisLabel

	/**
	 * @return Returns the y_AxisLabel.
	 */
	public String getY_AxisLabel() {
		return builder.getYAxisLabel();
	} // getY_AxisLabel

	/**
	 * @param axisLabel
	 *            The y_AxisLabel to set.
	 */
	public void setY_AxisLabel(String axisLabel) {
		builder.setYAxisLabel(axisLabel);
	} // setY_AxisLabel

	/**
	 * @return Returns the y_TargetLabel.
	 */
	public String getY_TargetLabel() {
		return m_Y_TargetLabel;
	} // getY_TargetLabel

	/**
	 * @param targetLabel
	 *            The y_TargetLabel to set.
	 */
	public void setY_TargetLabel(String targetLabel, double target) {
		m_Y_TargetLabel = targetLabel;
		// m_Y_Target = target;
	} // setY_TargetLabel

	/**
	 * @return zoom in factor
	 */
	public int getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * set zoom in factor
	 * 
	 * @param zoomFactor
	 */
	public void setZoomFactor(int zoomFactor) {
		this.zoomFactor = zoomFactor;
	}
	
	/**
	 * @return true if the summary table for performance goal is render on screen
	 */
	public boolean isRenderTable() {
		return m_renderTable;
	}

	/**
	 * hide/show the summary table for performance goal
	 * @param mRenderTable
	 */
	public void setRenderTable(boolean mRenderTable) {
		m_renderTable = mRenderTable;
	}

	/**
	 * @return true if chart is render on screen
	 */
	public boolean isRenderChart() {
		return m_renderChart;
	}

	/**
	 * hide/show chart for performance goal
	 * @param mRenderChart
	 */
	public void setRenderChart(boolean mRenderChart) {
		m_renderChart = mRenderChart;
	}

	/**************************************************************************
	 * Paint Component
	 * 
	 * @param g
	 *            graphics
	 */

	public void chartMouseClicked(int index) {
		GraphColumn bgc = list.get(index);
		if (null == bgc)
			return;
		MQuery query = bgc.getMQuery(builder.getMGoal());
		if (query != null)
			AEnv.zoom(query);
		else
			log.warning("Nothing to zoom to - " + bgc);
	}

	public void chartMouseMoved(ChartMouseEvent event) {
	}

	/**
	 * 
	 * @return GraphColumn[]
	 */
	public GraphColumn[] getGraphColumnList() {
		GraphColumn[] array = new GraphColumn[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	private void renderTable(Component parent) {
		Div div = new Div();
		appendChild(div);
		div.setSclass("pa-content");
		parent.appendChild(div);

		Table table = new Table();
		table.setSclass("pa-dataGrid");
		div.appendChild(table);
		Tr tr = new Tr();
		table.appendChild(tr);
		Td td = new Td();
		td.setSclass("pa-label");
		tr.appendChild(td);
		Text text = new Text("Target");
		td.appendChild(text);
		td = new Td();
		td.setDynamicProperty("colspan", "2");
		td.setSclass("pa-tdcontent");
		tr.appendChild(td);
		text = new Text(builder.getMGoal().getMeasureTarget().setScale(2,
				BigDecimal.ROUND_HALF_UP).toPlainString());
		td.appendChild(text);

		tr = new Tr();
		table.appendChild(tr);
		td = new Td();
		td.setSclass("pa-label");
		tr.appendChild(td);
		text = new Text("Actual");
		td.appendChild(text);
		td = new Td();
		td.setDynamicProperty("colspan", "2");
		td.setSclass("pa-tdcontent");
		tr.appendChild(td);
		text = new Text(builder.getMGoal().getMeasureActual().setScale(2,
				BigDecimal.ROUND_HALF_UP).toPlainString());
		td.appendChild(text);

		GraphColumn[] bList = getGraphColumnList();

		tr = new Tr();
		table.appendChild(tr);
		td = new Td();
		tr.appendChild(td);
		td.setDynamicProperty("rowspan", bList.length);
		td.setSclass("pa-label");
		td.setDynamicProperty("valign", "top");
		text = new Text(builder.getMGoal().getXAxisText());
		td.appendChild(text);

		for (int k = 0; k < bList.length; k++) {
			GraphColumn bgc = bList[k];
			if (k > 0) {
				tr = new Tr();
				table.appendChild(tr);
			}

			td = new Td();
			td.setSclass("pa-tdlabel");
			tr.appendChild(td);
			text = new Text(bgc.getLabel());
			td.appendChild(text);
			td = new Td();
			td.setSclass("pa-tdvalue");
			tr.appendChild(td);
			BigDecimal value = new BigDecimal(bgc.getValue());
			if (bgc.getMQuery(builder.getMGoal()) != null) {				
				A a = new A();
				a.setSclass("pa-hrefNode");
				td.appendChild(a);
				a.setId(ZOOM_KEY + k);
				a.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event event) throws Exception {
						Component comp = event.getTarget();
						String id = comp.getId();
						if (id.startsWith(ZOOM_KEY)) {
							String ss = id.substring(ZOOM_KEY.length());
							int index = Integer.parseInt(String.valueOf(ss));
							GraphColumn[] colList = getGraphColumnList();
							if ((index >= 0) && (index < colList.length))
								AEnv.zoom(colList[index].getMQuery(builder
										.getMGoal()));
						}
					}

				});
				a.setDynamicProperty("href", "javascript:;");
				text = new Text(value.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
				a.appendChild(text);

			} else {
				text = new Text(value.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
			}
		}
		tr = new Tr();
		table.appendChild(tr);
		td = new Td();
		td.setDynamicProperty("colspan", "3");
		tr.appendChild(td);
		text = new Text(builder.getMGoal().getDescription());
		td.appendChild(text);
		Br br = new Br();
		td.appendChild(br);
		text = new Text(stripHtml(builder.getMGoal().getColorSchema()
						.getDescription(), true));
		td.appendChild(text);				
	}

	private String stripHtml(String htmlString, boolean all) {
		htmlString = htmlString.replace("<html>", "").replace("</html>", "")
				.replace("<body>", "").replace("</body>", "").replace("<head>",
						"").replace("</head>", "");

		if (all)
			htmlString = htmlString.replace(">", "&gt;").replace("<", "&lt;");
		return htmlString;
	}
} // BarGraph
