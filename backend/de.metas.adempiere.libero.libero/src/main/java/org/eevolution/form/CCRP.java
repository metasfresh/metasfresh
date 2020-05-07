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
 *****************************************************************************/

package org.eevolution.form;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.images.Images;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.search.InfoBuilder;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MResource;
import org.compiere.model.MUOM;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.form.action.PopupAction;
import org.eevolution.form.action.ZoomMenuAction;
import org.eevolution.form.crp.CRPDatasetFactory;
import org.eevolution.form.crp.CRPModel;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.tools.swing.SwingTool;
import org.eevolution.tools.worker.SingleWorker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;

import de.metas.i18n.Msg;
import de.metas.material.planning.IResourceDAO;
import de.metas.product.ResourceId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;

/**
 * Capacity Requirement Planning Form
 * 
 * AD_FORM:
 * INSERT INTO ad_form VALUES (1000020, 0, 0, 'Y', CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 100, 'Resource Load View', NULL, NULL, '3', 'org.compiere.mfg.form.CCRP', 'U', 'N', NULL);
 *
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class CCRP extends CAbstractForm
{

	class ActionHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			{

				SwingTool.setCursorsFromParent(getWindow(), true);

				final ActionEvent evt = e;
				worker = new SingleWorker()
				{

					@Override
					protected Object doIt()
					{

						handleActionEvent(evt);
						return null;
					}
				};
				worker.start();
			}
			if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			{

				dispose();
			}
		}
	}

	class TreeHandler extends MouseInputAdapter implements TreeSelectionListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{

			if (model.getTree().getPathForLocation(e.getX(), e.getY()) == null)
			{

				return;
			}

			SwingTool.setCursorsFromChild(e.getComponent(), true);

			final MouseEvent evt = e;
			worker = new SingleWorker()
			{

				@Override
				protected Object doIt()
				{

					handleTreeEvent(evt);
					return null;
				}
			};

			worker.start();
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{

			// m_tree.setToolTipText(msg.getToolTipText(e));
		}

		@Override
		public void valueChanged(TreeSelectionEvent event)
		{
		}
	}

	class FrameHandler extends WindowAdapter
	{

		@Override
		public void windowClosing(WindowEvent e)
		{

			dispose();
		}
	}

	class LabelGenerator extends StandardCategoryItemLabelGenerator
	{

		public String generateItemLabel(CategoryDataset categorydataset, int i, int j)
		{

			return categorydataset.getRowKey(i).toString();
		}

	}

	private VLookup resource;
	private VDate dateFrom;
	private VDate dateTo;
	private ChartPanel chartPanel;
	private JSplitPane contentPanel;

	private SingleWorker worker;

	protected CRPModel model;
	protected JPopupMenu popup;

	public CCRP()
	{

		super();
	}

	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		super.init(WindowNo, frame);

		fillPicks();
		jbInit();

		if (frame != null)
		{
			frame.setIconImage(Images.getImage2(InfoBuilder.ACTION_InfoCRP + "16"));
		}
	}

	private void jbInit()
	{
		dateFrom = new VDate("DateFrom", true, false, true, DisplayType.Date, "DateFrom");
		dateTo = new VDate("DateTo", true, false, true, DisplayType.Date, "DateTo");

		CPanel northPanel = new CPanel();
		northPanel.setLayout(new java.awt.GridBagLayout());

		northPanel.add(
				new CLabel(Msg.translate(Env.getCtx(), "S_Resource_ID")),
				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(
				resource,
				new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		northPanel.add(
				new CLabel(Msg.translate(Env.getCtx(), "DateFrom")),
				new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(
				dateFrom,
				new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		northPanel.add(
				new CLabel(Msg.translate(Env.getCtx(), "DateTo")),
				new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(
				dateTo,
				new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
		confirmPanel.setActionListener(new ActionHandler());

		contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		contentPanel.setPreferredSize(new Dimension(800, 600));

		getWindow().getContentPane().add(northPanel, BorderLayout.NORTH);
		getWindow().getContentPane().add(contentPanel, BorderLayout.CENTER);
		getWindow().getContentPane().add(confirmPanel, BorderLayout.SOUTH);
	}

	private void fillPicks()
	{

		Properties ctx = Env.getCtx();

		// Hardcoded Column ID - Manufacturing Resource ID
		MLookup resourceL = MLookupFactory.get(ctx, getWindowNo(), 0, MColumn.getColumn_ID(MResource.Table_Name, "S_Resource_ID"), DisplayType.TableDir);
		resource = new VLookup("S_Resource_ID", false, false, true, resourceL);
	}

	protected JPopupMenu createPopup(JTree tree)
	{

		JPopupMenu pm = new JPopupMenu();
		PopupAction action = null;

		try
		{

			action = new ZoomMenuAction(tree);
			pm.add(action);
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

		return pm;
	}

	private void handleTreeEvent(MouseEvent e)
	{

		if (e.getButton() == MouseEvent.BUTTON3)
		{

			model.getTree().setSelectionPath(model.getTree().getPathForLocation(e.getX(), e.getY()));

			DefaultMutableTreeNode node = (DefaultMutableTreeNode)model.getTree().getSelectionPath().getLastPathComponent();

			if (!(node.getUserObject() instanceof Date) && !(node.getUserObject() instanceof I_PP_Order_Node))
			{

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		SwingTool.setCursorsFromChild(e.getComponent(), false);
	}

	private void handleActionEvent(ActionEvent e)
	{

		LocalDateTime df = TimeUtil.asLocalDateTime(getDateFrom());
		LocalDateTime dt = TimeUtil.asLocalDateTime(getDateTo());
		ResourceId resourceId = getResourceId();

		if (df != null && dt != null && resourceId != null)
		{

			model = CRPDatasetFactory.get(df, dt, resourceId);

			JFreeChart jfreechart = createChart(model.getDataset(), getChartTitle(), getSourceUOM());

			chartPanel = new ChartPanel(jfreechart, false);
			contentPanel.setLeftComponent(chartPanel);

			JTree tree = model.getTree();
			tree.addMouseListener(new TreeHandler());
			contentPanel.setRightComponent(new JScrollPane(tree));
			popup = createPopup(tree);

			contentPanel.setVisible(true);

			contentPanel.validate();
			contentPanel.repaint();
		}

		SwingTool.setCursorsFromParent(getWindow(), false);
	}

	private String getChartTitle()
	{

		I_S_Resource r = getResource();
		String title = r.getName() != null ? r.getName() : "";
		title = title + " " + r.getDescription() != null ? r.getDescription() : "";

		return title;
	}

	public Timestamp getDateFrom()
	{

		Timestamp t = null;

		if (dateFrom.getValue() != null)
		{

			t = dateFrom.getValue();
		}

		return t;
	}

	public Timestamp getDateTo()
	{

		Timestamp t = null;

		if (dateTo.getValue() != null)
		{

			t = dateTo.getValue();
		}

		return t;
	}

	public I_C_UOM getSourceUOM()
	{
		final ResourceId resourceId = getResourceId();
		final UomId uomId = Services.get(IResourceDAO.class).getResourceTypeByResourceId(resourceId).getDurationUomId();
		return Services.get(IUOMDAO.class).getById(uomId);
	}

	private ResourceId getResourceId()
	{
		if (resource.getValue() == null)
		{
			return null;
		}
		final int resourceRepoId = (Integer)resource.getValue();
		return ResourceId.ofRepoIdOrNull(resourceRepoId);
	}

	public I_S_Resource getResource()
	{
		ResourceId resourceId = getResourceId();
		if(resourceId == null)
		{
			return null;
		}
		
		return Services.get(IResourceDAO.class).getById(resourceId);
	}

	public MUOM getTargetUOM()
	{
		MUOM u = null;
		if (resource.getValue() != null)
		{
			u = MUOM.get(Env.getCtx(), ((Integer)resource.getValue()).intValue());
		}
		return u;
	}

	private JFreeChart createChart(CategoryDataset dataset, String title, I_C_UOM uom)
	{

		JFreeChart chart = ChartFactory.createBarChart3D(
				title,
				Msg.translate(Env.getCtx(), "Day"),    			// X-Axis label
				Msg.translate(Env.getCtx(), (uom == null) ? "" : uom.getName()),   	// Y-Axis label
				dataset,         								// Dataset
				PlotOrientation.VERTICAL, 						// orientation
				true,                     						// include legend
				true,                     						// tooltips?
				false                     						// URLs?
		);

		chart.setBackgroundPaint(Color.WHITE);
		chart.setAntiAlias(true);
		chart.setBorderVisible(true);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.GRAY);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.GRAY);

		BarRenderer3D barrenderer = (BarRenderer3D)plot.getRenderer();
		barrenderer.setDrawBarOutline(false);
		barrenderer.setBaseItemLabelGenerator(new LabelGenerator());
		// barrenderer.setBaseLabelGenerator(new LabelGenerator());
		barrenderer.setBaseItemLabelsVisible(true);
		barrenderer.setSeriesPaint(0, new Color(10, 80, 150, 128));
		barrenderer.setSeriesPaint(1, new Color(180, 60, 50, 128));

		ItemLabelPosition itemlabelposition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		barrenderer.setPositiveItemLabelPosition(itemlabelposition);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

		return chart;
	}

	@Override
	public void dispose()
	{

		super.dispose();

		if (resource != null)
		{

			resource.dispose();
		}
		resource = null;

		if (dateFrom != null)
		{

			dateFrom.dispose();
		}
		dateFrom = null;

		if (dateTo != null)
		{

			dateTo.dispose();
		}
		dateTo = null;

		if (worker != null)
		{

			worker.stop();
		}
		worker = null;

		chartPanel = null;
		contentPanel = null;
		popup = null;
	}
}
