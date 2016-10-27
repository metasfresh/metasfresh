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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.AWindow;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.PAttributeInstance;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MLot;
import org.compiere.model.MLotCtl;
import org.compiere.model.MQuery;
import org.compiere.model.MSerNoCtl;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CEditor;
import org.compiere.swing.CLabel;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.adempiere.form.IClientUI;

/**
 * Product Attribute Set Product/Instance Dialog Editor.
 * Called from VPAttribute.actionPerformed
 *
 * @author Jorg Janke
 * @version $Id: VPAttributeDialog.java,v 1.4 2006/07/30 00:51:27 jjanke Exp $
 */
public class VPAttributeDialog extends CDialog
		implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1062346984681892620L;

	// services
	private final transient Logger log = LogManager.getLogger(getClass());
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributeExcludeBL attributeExcludeBL = Services.get(IAttributeExcludeBL.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);

	// private static final String COLUMNNAME_ASILastUpdated = "ASILastUpdated";

	/*****************************************************************************
	 * Mouse Listener for Popup Menu
	 */
	static final class VPAttributeDialog_mouseAdapter extends java.awt.event.MouseAdapter
	{
		/**
		 * Constructor
		 *
		 * @param adaptee adaptee
		 */
		VPAttributeDialog_mouseAdapter(VPAttributeDialog adaptee)
		{
			m_adaptee = adaptee;
		}	// VPAttributeDialog_mouseAdapter

		private VPAttributeDialog m_adaptee;

		/**
		 * Mouse Listener
		 *
		 * @param e MouseEvent
		 */
		@Override
		public void mouseClicked(MouseEvent e)
		{
			// System.out.println("mouseClicked " + e.getID() + " " + e.getSource().getClass().toString());
			// popup menu
			if (SwingUtilities.isRightMouseButton(e))
				m_adaptee.popupMenu.show((Component)e.getSource(), e.getX(), e.getY());
		}	// mouse Clicked

	}	// VPAttributeDialog_mouseAdapter

	/**
	 * Product Attribute Instance Dialog
	 *
	 * @param frame parent frame
	 * @param M_AttributeSetInstance_ID Product Attribute Set Instance id
	 * @param M_Product_ID Product id
	 * @param C_BPartner_ID b partner
	 * @param productWindow this is the product window (define Product Instance)
	 * @param AD_Column_ID column
	 * @param WindowNo window
	 */
	public VPAttributeDialog(final Frame frame,
			final int M_AttributeSetInstance_ID,
			final int M_Product_ID,
			final boolean productWindow,
			final int AD_Column_ID,
			final IVPAttributeContext attributeContext)
	{
		super(frame, Services.get(IMsgBL.class).translate(Env.getCtx(), "M_AttributeSetInstance_ID"), true);

		log.info("M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID
				+ ", M_Product_ID=" + M_Product_ID
				+ ", IsProductWindow=" + productWindow
				+ ", Column=" + AD_Column_ID);

		m_WindowNo = Env.createWindowNo(this);
		// m_M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		m_M_Product_ID = M_Product_ID;
		m_productWindow = productWindow;
		m_AD_Column_ID = AD_Column_ID;
		this.attributeContext = attributeContext;

		//
		// Initialize
		try
		{
			jbInit();

			// Dynamic Init
			asiTemplate = loadASITemplate(M_AttributeSetInstance_ID);
			if (asiTemplate == null)
			{
				dispose();
				return;
			}

			// Init all UI editors and fields based on ASI template
			initAttributes();

			AEnv.showCenterWindow(frame, this);
		}
		catch (Exception ex)
		{
			clientUI.error(m_WindowNo, ex);
			dispose();
		}
	}	// VPAttributeDialog

	private final IVPAttributeContext attributeContext;
	/**
	 * VPAttributeDialog's context.
	 * NOTE: this is not the parent window No
	 */
	private final int m_WindowNo;
	private MAttributeSetInstance asiTemplate;
	private MAttributeSetInstance asiEdited = null;
	private int m_M_Locator_ID;
	private final List<MAttribute> m_attributes = new ArrayList<>();
	private final int m_M_Product_ID;
	private final int m_AD_Column_ID;
	/** Enter Product Attributes */
	private final boolean m_productWindow;

	/** Row Counter */
	private int m_row = 0;
	/** List of Editors */
	private Map<Integer, CEditor> attributeId2editor = new HashMap<>();
	/** Length of Instance value (40) */
	private static final int INSTANCE_VALUE_LENGTH = 40;

	private CButton bSelectExistingASI = new CButton(Images.getImageIcon2("PAttribute16"));
	// Lot
	private VString fieldLotString = new VString("Lot", false, false, true, 20, 20, null, null);
	private CComboBox<KeyNamePair> fieldLot = null;
	private CButton bLot = new CButton(msgBL.getMsg(Env.getCtx(), "New"));
	// Lot Popup
	JPopupMenu popupMenu = new JPopupMenu();
	private CMenuItem mZoom;
	// Ser No
	private VString fieldSerNo = new VString("SerNo", false, false, true, 20, 20, null, null);
	private CButton bSerNo = new CButton(msgBL.getMsg(Env.getCtx(), "New"));
	// Date
	private final VDate fieldGuaranteeDate = new VDate("GuaranteeDate", false, false, true, DisplayType.Date, msgBL.translate(Env.getCtx(), "GuaranteeDate"));
	/** True if the ASI's GuaranteeDate field is displayed and we need to handle it (load/save). */
	private boolean fieldGuaranteeDateDisplayed = false;
	//
	private CTextField fieldDescription = new CTextField(20);
	//
	private CPanel centerPanel = new CPanel();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	/**
	 * Layout
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		centerPanel.setLayout(new ALayout(5, 5, true));
		//
		confirmPanel.setActionListener(this);
	}	// jbInit

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private int getM_Product_ID()
	{
		return m_M_Product_ID;
	}

	private boolean isProductWindow()
	{
		return m_productWindow;
	}

	private boolean isProcessParameter()
	{
		return m_AD_Column_ID <= 0;
	}

	private boolean isPureProductASI()
	{
		final boolean isPureProductASI = !isProductWindow()
				&& (getM_Product_ID() > 0);
		return isPureProductASI;
	}

	private boolean isAllowSelectExistingASI()
	{
		final boolean allowSelectExistingASI = isPureProductASI() && getM_Product_ID() > 0;
		return allowSelectExistingASI;
	}

	/**
	 * Loads the ASI template to be used.
	 *
	 * @param fromAttributeSetInstanceId
	 * @return <ul>
	 *         <li>ASI template
	 *         <li> <code>null</code> if this is not a valid settings and we need to dispose the dialog
	 *         </ul>
	 * @throws AdempiereException if something failed
	 */
	private final MAttributeSetInstance loadASITemplate(final int fromAttributeSetInstanceId)
	{
		final Properties ctx = getCtx();
		final int productId = getM_Product_ID();
		final boolean isPureProductASI = isPureProductASI();

		//
		// If there is not product specified
		// and we need a pure product ASI (i.e. not the ASI that we configure on product level)
		// => this dialog does not make sense and we need to dispose it ASAP
		// TODO: in future we shall do this checking BEFORE we reach this point
		if (productId <= 0 && isPureProductASI)
		{
			return null;
		}

		final MAttributeSetInstance asiTemplate;

		//
		// Load/Create the ASI
		// Get the M_AttributeSet.
		MAttributeSet as = null;
		if (productId > 0)
		{
			// Get/Create the ASI
			asiTemplate = MAttributeSetInstance.get(ctx, fromAttributeSetInstanceId, productId);
			if (asiTemplate == null)
			{
				throw new AdempiereException("@NotFound@ @M_AttributeSetInstance_ID@ (@M_Product_ID@=" + productId + ")");
			}
			Env.setContext(ctx, m_WindowNo, "M_AttributeSet_ID", asiTemplate.getM_AttributeSet_ID());

			// Get Attribute Set
			as = asiTemplate.getMAttributeSet();
		}
		else
		{
			final int M_AttributeSet_ID = attributeContext.getM_AttributeSet_ID();
			asiTemplate = new MAttributeSetInstance(ctx, 0, M_AttributeSet_ID, ITrx.TRXNAME_None); // new ASI
			as = asiTemplate.getMAttributeSet();
			if (as == null && M_AttributeSet_ID == 0)
			{
				// FIXME: workaround to deal with M_AttributeSet_ID=0 which is an existing record
				as = queryBL.createQueryBuilder(I_M_AttributeSet.class, ctx, ITrx.TRXNAME_None)
						.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID, 0)
						.create()
						.firstOnlyNotNull(MAttributeSet.class);
				asiTemplate.setMAttributeSet(as);
			}
		}
		// Product has no Attribute Set
		if (as == null)
		{
			throw new AdempiereException("@PAttributeNoAttributeSet@");
		}
		// Product has no Instance Attributes
		if (isPureProductASI && !as.isInstanceAttribute())
		{
			throw new AdempiereException("@PAttributeNoInstanceAttribute@");
		}

		return asiTemplate;
	}

	/**
	 * Initialize all panel fields and editors based on {@link #asiTemplate}.
	 */
	private final void initAttributes()
	{
		final Properties ctx = getCtx();
		final boolean isProductWindow = isProductWindow();
		final boolean isProcessParameter = isProcessParameter();
		final boolean isPureProductASI = isPureProductASI();
		final boolean allowSelectExistingASI = isAllowSelectExistingASI();
		final MAttributeSet as = asiTemplate.getMAttributeSet();
		Check.assumeNotNull(as, "attribute set not null");
		final boolean isASITemplateNew = asiTemplate.getM_AttributeSetInstance_ID() <= 0;

		//
		// Show Select existing ASI (if allowed)
		if (allowSelectExistingASI)
		{
			// Select existing ASI button
			bSelectExistingASI.setText(msgBL.getMsg(ctx, "SelectExisting"));
			bSelectExistingASI.addActionListener(this);
			centerPanel.add(bSelectExistingASI, new ALayoutConstraint(m_row++, 1));
		}

		//
		// Fetch M_Attributes
		final List<MAttribute> attributes;
		if (isProductWindow)
		{
			attributes = Arrays.asList(as.getMAttributes(false)); // non-instance attributes
		}
		else if (isPureProductASI)
		{
			// Regular product's attribute set instance attributes
			attributes = Arrays.asList(as.getMAttributes(true)); // all instance attributes
		}
		else if (isProcessParameter)
		{
			final IQueryBuilder<MAttribute> attributesQueryBuilder = queryBL
					.createQueryBuilder(MAttribute.class, ctx, ITrx.TRXNAME_None)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient();
			attributesQueryBuilder.orderBy()
					.addColumn(I_M_Attribute.COLUMNNAME_Name)
					.addColumn(I_M_Attribute.COLUMNNAME_M_Attribute_ID);
			attributes = attributesQueryBuilder
					.create()
					.list(MAttribute.class);
		}
		else
		{
			attributes = Collections.emptyList();
		}

		//
		// Create attributes UI editors
		for (final MAttribute attribute : attributes)
		{
			if (!attributeExcludeBL.isExcludedAttribute(attribute, as, m_AD_Column_ID, attributeContext.isSOTrx()))
			{
				addAttributeLine(attribute);
			}
		}

		//
		// Lot
		if (isPureProductASI && as.isLot())
		{
			CLabel label = new CLabel(msgBL.translate(ctx, "Lot"));
			label.setLabelFor(fieldLotString);
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldLotString, null);
			fieldLotString.setText(asiTemplate.getLot());
			// M_Lot_ID
			// int AD_Column_ID = 9771; // M_AttributeSetInstance.M_Lot_ID
			// fieldLot = new VLookup ("M_Lot_ID", false,false, true,
			// MLookupFactory.get(getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.TableDir));
			final String sql = "SELECT M_Lot_ID, Name "
					+ "FROM M_Lot l "
					+ "WHERE EXISTS (SELECT M_Product_ID FROM M_Product p "
					+ "WHERE p.M_AttributeSet_ID=" + asiTemplate.getM_AttributeSet_ID()
					+ " AND p.M_Product_ID=l.M_Product_ID)";
			fieldLot = new CComboBox<>(DB.getKeyNamePairs(sql, true));
			label = new CLabel(msgBL.translate(ctx, "M_Lot_ID"));
			label.setLabelFor(fieldLot);
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldLot, null);
			if (asiTemplate.getM_Lot_ID() > 0)
			{
				for (int i = 1; i < fieldLot.getItemCount(); i++)
				{
					KeyNamePair pp = fieldLot.getItemAt(i);
					if (pp.getKey() == asiTemplate.getM_Lot_ID())
					{
						fieldLot.setSelectedIndex(i);
						fieldLotString.setEditable(false);
						break;
					}
				}
			}
			fieldLot.addActionListener(this);
			// New Lot Button
			if (asiTemplate.getMAttributeSet().getM_LotCtl_ID() > 0)
			{
				if (Env.getUserRolePermissions().isTableAccess(MLot.Table_ID, false)
						&& Env.getUserRolePermissions().isTableAccess(MLotCtl.Table_ID, false)
						&& !asiTemplate.isExcludeLot(m_AD_Column_ID, attributeContext.isSOTrx()))
				{
					centerPanel.add(bLot, null);
					bLot.addActionListener(this);
				}
			}
			// Popup
			fieldLot.addMouseListener(new VPAttributeDialog_mouseAdapter(this));    // popup
			mZoom = new CMenuItem(msgBL.getMsg(ctx, "Zoom"), Images.getImageIcon2("Zoom16"));
			mZoom.addActionListener(this);
			popupMenu.add(mZoom);
		}	// Lot

		//
		// SerNo
		if (isPureProductASI && as.isSerNo())
		{
			CLabel label = new CLabel(msgBL.translate(ctx, "SerNo"));
			label.setLabelFor(fieldSerNo);
			fieldSerNo.setText(asiTemplate.getSerNo());
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldSerNo, null);
			// New SerNo Button
			if (asiTemplate.getMAttributeSet().getM_SerNoCtl_ID() != 0)
			{
				if (Env.getUserRolePermissions().isTableAccess(MSerNoCtl.Table_ID, false)
						&& !asiTemplate.isExcludeSerNo(m_AD_Column_ID, attributeContext.isSOTrx()))
				{
					centerPanel.add(bSerNo, null);
					bSerNo.addActionListener(this);
				}
			}
		}	// SerNo

		//
		// GuaranteeDate.
		// We are displaying it if we deal with a pure product ASI (i.e. user is not editing the ASI from product window),
		// and if:
		// * the attribute set requires a GuaranteeDate
		// * or if the ASI has a GuaranteeDate already set
		if (isPureProductASI && (as.isGuaranteeDate() || asiTemplate.getGuaranteeDate() != null))
		{
			CLabel label = new CLabel(msgBL.translate(ctx, "GuaranteeDate"));
			label.setLabelFor(fieldGuaranteeDate);
			if (isASITemplateNew)
			{
				Date guaranteeDate = asiTemplate.getGuaranteeDate();
				if (guaranteeDate == null)
				{
					guaranteeDate = attributesBL.calculateBestBeforeDate(ctx,
							m_M_Product_ID, // product
							attributeContext.getC_BPartner_ID(), // vendor bpartner
							Env.getDate(ctx) // dateReceipt
							);
				}
				fieldGuaranteeDate.setValue(guaranteeDate);
			}
			else
			{
				fieldGuaranteeDate.setValue(asiTemplate.getGuaranteeDate());
			}
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldGuaranteeDate, null);
			fieldGuaranteeDateDisplayed = true;
		}	// GuaranteeDate

		// Make sure we have at least something to edit or something to select,
		// else there is no point in showing empty this window.
		if (m_row == 0)
		{
			throw new AdempiereException("@PAttributeNoInfo@");
		}

		//
		// New/Edit Window
		if (allowSelectExistingASI)
		{
			setReadWrite(true);
		}

		//
		// Attrribute Set Instance Description
		{
			final CLabel labelDescription = new CLabel(msgBL.translate(ctx, "Description"));
			labelDescription.setLabelFor(fieldDescription);
			fieldDescription.setText(asiTemplate.getDescription());
			fieldDescription.setEditable(false);
			centerPanel.add(labelDescription, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldDescription, null);
		}

		// Window usually to wide (??)
		{
			final Dimension dd = centerPanel.getPreferredSize();
			dd.width = Math.min(500, dd.width);
			centerPanel.setPreferredSize(dd);
		}
	}	// initAttribute

	/**
	 * Add Attribute Line
	 *
	 * @param attribute attribute
	 * @param product product level attribute
	 * @param readOnly value is read only
	 */
	private void addAttributeLine(final MAttribute attribute)
	{
		final boolean product = m_productWindow;
		final boolean readOnly = false;

		log.debug(attribute + ", Product=" + product + ", R/O=" + readOnly);
		CLabel label = new CLabel(attribute.getName());
		if (product)
		{
			label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize()));
		}
		if (attribute.getDescription() != null)
		{
			label.setToolTipText(attribute.getDescription());
		}

		centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
		//
		final int attributeSetInstanceId = asiTemplate.getM_AttributeSetInstance_ID();
		final int attributeId = attribute.getM_Attribute_ID();
		final MAttributeInstance instance = attribute.getMAttributeInstance(attributeSetInstanceId);
		if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			InterfaceWrapperHelper.setDynAttribute(attribute, Env.DYNATTR_WindowNo, attributeContext.getWindowNo());
			InterfaceWrapperHelper.setDynAttribute(attribute, Env.DYNATTR_TabNo, attributeContext.getTabNo()); // tabNo

			final I_M_AttributeValue[] values = attribute.getMAttributeValues(getSOTrx());	// optional = null
			final CComboBox<I_M_AttributeValue> editor = new CComboBox<>(values);
			boolean found = false;
			if (instance != null && instance.getM_AttributeValue_ID() > 0)
			{
				for (int i = 0; i < values.length; i++)
				{
					if (values[i] != null && values[i].getM_AttributeValue_ID() == instance.getM_AttributeValue_ID())
					{
						editor.setSelectedIndex(i);
						found = true;
						break;
					}
				}
				if (found)
					log.debug("Attribute=" + attribute.getName() + " #" + values.length + " - found: " + instance);
				else
					log.warn("Attribute=" + attribute.getName() + " #" + values.length + " - NOT found: " + instance);
			}	// setComboBox
			else
			{
				log.debug("Attribute=" + attribute.getName() + " #" + values.length + " no instance");
			}
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
				editor.setEnabled(false);
			else
				attributeId2editor.put(attributeId, editor);
		}
		else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attribute.getAttributeValueType()))
		{
			final VNumber editor = new VNumber(
					attribute.getName(), // ColumnName
					attribute.isMandatory(), // mandatory
					false, // IsReadOnly
					true, // IsUpdateable
					DisplayType.Number, // DisplayType
					attribute.getName() // Title
			);
			if (instance != null)
			{
				if (InterfaceWrapperHelper.isNull(instance, I_M_AttributeInstance.COLUMNNAME_ValueNumber))
				{
					editor.setValue(null);
				}
				else
				{
					editor.setValue(instance.getValueNumber());
				}
			}
			else
			{
				// better don't set a default value
				// editor.setValue(Env.ZERO);
			}
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
				editor.setEnabled(false);
			else
				attributeId2editor.put(attributeId, editor);
		}
		else if (MAttribute.ATTRIBUTEVALUETYPE_Date.equals(attribute.getAttributeValueType()))
		{
			final VDate editor = new VDate(
					attribute.getName(), // columnName
					attribute.isMandatory(), // mandatory
					false, // isReadOnly
					true, // isUpdateable
					DisplayType.Date, // displayType
					attribute.getName() // title
			);
			if (instance != null)
				editor.setValue(instance.getValueDate());
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
				editor.setEnabled(false);
			else
				attributeId2editor.put(attributeId, editor);
		}
		else
		// Text Field
		{
			VString editor = new VString(
					attribute.getName(), // ColumnName
					attribute.isMandatory(), // mandatory
					false, // isReadOnly
					true, // isUpdateable
					20, // displayLength
					INSTANCE_VALUE_LENGTH, // fieldLength
					null, // VFormat
					null // ObscureType
			);
			if (instance != null)
				editor.setText(instance.getValue());
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
				editor.setEnabled(false);
			else
				attributeId2editor.put(attributeId, editor);
		}

		//
		// Add our attribute to the list of attributes
		m_attributes.add(attribute);
	}	// addAttributeLine

	private Boolean getSOTrx()
	{
		return attributeContext.getSOTrx();
	}

	/**
	 * Sets the given result (which will be returned to caller) and dispose this dialog.
	 *
	 * @param asi
	 * @param M_Locator_ID
	 */
	private final void setResultAndDispose(final MAttributeSetInstance asi, int M_Locator_ID)
	{
		this.asiEdited = asi;
		this.m_M_Locator_ID = M_Locator_ID;
		dispose();
	}

	/**
	 * dispose
	 */
	@Override
	public void dispose()
	{
		removeAll();

		Env.clearWinContext(m_WindowNo);

		//
		// FIXME: we need to set the Info on parent's WindowNo
		//
		// String columnName = DB.getSQLValueString(ITrx.TRXNAME_None, "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID = ?", m_AD_Column_ID);
		// if (Check.isEmpty(columnName, true))
		// {
		// // fallback
		// columnName = "M_AttributeSetInstance_ID";
		// }
		// Env.setContext(getCtx(), m_WindowNo, Env.TAB_INFO, m_columnName, String.valueOf(m_M_AttributeSetInstance_ID));
		// Env.setContext(getCtx(), m_WindowNo, Env.TAB_INFO, "M_Locator_ID", String.valueOf(m_M_Locator_ID));
		//
		super.dispose();
	}	// dispose

	/**
	 * ActionListener
	 *
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (Exception ex)
		{
			clientUI.warn(m_WindowNo, ex);
		}
	}

	private final void actionPerformed0(ActionEvent e) throws Exception
	{
		// Select Instance
		if (e.getSource() == bSelectExistingASI)
		{
			cmd_select();
			return;
		}
		// Select Lot from existing
		else if (e.getSource() == fieldLot)
		{
			final KeyNamePair pp = fieldLot.getSelectedItem();
			if (pp != null && pp.getKey() != -1)
			{
				fieldLotString.setText(pp.getName());
				fieldLotString.setEditable(false);
				asiTemplate.setM_Lot_ID(pp.getKey());
			}
			else
			{
				fieldLotString.setEditable(true);
				asiTemplate.setM_Lot_ID(0);
			}
		}
		// Create New Lot
		else if (e.getSource() == bLot)
		{
			KeyNamePair pp = asiTemplate.createLot(m_M_Product_ID);
			if (pp != null)
			{
				fieldLot.addItem(pp);
				fieldLot.setSelectedItem(pp);
			}
		}
		// Create New SerNo
		else if (e.getSource() == bSerNo)
		{
			fieldSerNo.setText(asiTemplate.getSerNo(true));
		}

		// OK
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			final MAttributeSetInstance asi = saveSelection();
			final int M_Locator_ID = -1; // N/A
			setResultAndDispose(asi, M_Locator_ID);
			return;
		}
		// Cancel
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			final int M_Locator_ID = -1; // N/A
			setResultAndDispose(null, M_Locator_ID);
		}
		// Zoom M_Lot
		else if (e.getSource() == mZoom)
		{
			cmd_zoom();
		}
		else
		{
			log.error("Unknown event: {}", e);
		}
	}	// actionPerformed

	/**
	 * Existing Attribute Set Instance Selection Button
	 *
	 * @return true if selected
	 */
	private void cmd_select()
	{
		log.info("");

		int M_Warehouse_ID = attributeContext.getM_Warehouse_ID();

		final int C_DocType_ID = attributeContext.getC_DocType_ID();
		if (C_DocType_ID > 0)
		{
			final MDocType doctype = new MDocType(getCtx(), C_DocType_ID, ITrx.TRXNAME_None);
			String docbase = doctype.getDocBaseType();
			if (docbase.equals(MDocType.DOCBASETYPE_MaterialReceipt))
				M_Warehouse_ID = 0;
		}

		// teo_sarca [ 1564520 ] Inventory Move: can't select existing attributes
		int M_Locator_ID = 0;
		if (m_AD_Column_ID == 8551) // TODO: hardcoded: M_MovementLine[324].M_AttributeSetInstance_ID[8551]
		{
			M_Locator_ID = attributeContext.getM_Locator_ID();
		}

		String title = "";
		// Get Text
		final String sql = "SELECT p.Name, w.Name, w.M_Warehouse_ID FROM M_Product p, M_Warehouse w "
				+ "WHERE p.M_Product_ID=? AND w.M_Warehouse_ID"
				+ (M_Locator_ID <= 0 ? "=?" : " IN (SELECT M_Warehouse_ID FROM M_Locator where M_Locator_ID=?)"); // teo_sarca [ 1564520 ]
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, M_Locator_ID <= 0 ? M_Warehouse_ID : M_Locator_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				title = rs.getString(1) + " - " + rs.getString(2);
				M_Warehouse_ID = rs.getInt(3); // fetch the actual warehouse - teo_sarca [ 1564520 ]
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		// Open ASI selection window and wait for result
		final int bpartnerId = attributeContext.getC_BPartner_ID();
		final PAttributeInstance pai = new PAttributeInstance(this, title, M_Warehouse_ID, M_Locator_ID, m_M_Product_ID, bpartnerId);
		final MAttributeSetInstance selectedASI = pai.getM_AttributeSetInstance();
		if (selectedASI == null)
		{
			// user canceled => do nothing
			return;
		}

		setResultAndDispose(selectedASI, pai.getM_Locator_ID());
	}	// cmd_select

	/**
	 * Update which fields status (read-only/read-write).
	 */
	private final void setReadWrite(final boolean rw)
	{
		log.info("R/W={} {}", rw, asiTemplate);

		// Lot
		final boolean isNewLot = asiTemplate == null || asiTemplate.getM_Lot_ID() <= 0;
		fieldLotString.setEditable(rw && isNewLot);
		if (fieldLot != null)
		{
			fieldLot.setReadWrite(rw);
		}
		bLot.setReadWrite(rw);

		// Serial No
		fieldSerNo.setReadWrite(rw);
		bSerNo.setReadWrite(rw);

		// Guarantee Date
		fieldGuaranteeDate.setReadWrite(rw);

		// Attribute Editors
		for (final CEditor editor : attributeId2editor.values())
		{
			editor.setReadWrite(rw);
		}
	}	// cmd_newEdit

	/**
	 * Zoom M_Lot
	 */
	private void cmd_zoom()
	{
		int M_Lot_ID = 0;
		KeyNamePair pp = fieldLot.getSelectedItem();
		if (pp != null)
			M_Lot_ID = pp.getKey();
		MQuery zoomQuery = new MQuery("M_Lot");
		zoomQuery.addRestriction("M_Lot_ID", MQuery.EQUAL, M_Lot_ID);
		log.info(zoomQuery.toString());
		//
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//
		int AD_Window_ID = 257;		// Lot
		AWindow frame = new AWindow();
		if (frame.initWindow(AD_Window_ID, zoomQuery))
		{
			this.setVisible(false);
			this.setModal(false);	// otherwise blocked
			this.setVisible(true);
			AEnv.addToWindowManager(frame);
			AEnv.showScreen(frame, SwingConstants.EAST);
		}
		// async window - not able to get feedback
		frame = null;
		//
		setCursor(Cursor.getDefaultCursor());
	}	// cmd_zoom

	/**
	 * Save Selection
	 *
	 * @return asi which was saved or null
	 *
	 * @return true if saved
	 */
	private MAttributeSetInstance saveSelection()
	{
		final IMutable<MAttributeSetInstance> asiRef = new Mutable<>();
		trxManager.run(new TrxRunnableAdapter()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				final MAttributeSetInstance asi = saveSelection0();
				asiRef.setValue(asi);
			}
		});

		return asiRef.getValue();
	}

	private MAttributeSetInstance saveSelection0()
	{
		log.info("");

		final MAttributeSet as = asiTemplate.getMAttributeSet();
		Check.assumeNotNull(as, "as not null");

		// Create a new ASI which is copying the existing one
		final MAttributeSetInstance asi = new MAttributeSetInstance(getCtx(),
				0,
				ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.copyValues(asiTemplate, asi, false); // honorIsCalculated=false => copy everything
		asi.setM_AttributeSet(as); // make sure we have the right AttributeSet model set

		//
		boolean changed = false;
		final Set<String> mandatory = new LinkedHashSet<>();

		// Lot
		if (!m_productWindow && as.isLot())
		{
			String text = fieldLotString.getText();
			asi.setLot(text);
			if (as.isLotMandatory() && (text == null || text.length() == 0))
			{
				mandatory.add(msgBL.translate(getCtx(), "Lot"));
			}
			changed = true;
		}	// Lot

		// Serial No
		if (!m_productWindow && as.isSerNo())
		{
			final String serNo = fieldSerNo.getText();
			asi.setSerNo(serNo);
			if (as.isSerNoMandatory() && Check.isEmpty(serNo, true))
			{
				mandatory.add(msgBL.translate(getCtx(), "SerNo"));
			}
			changed = true;
		}	// SerNo

		//
		// Guarantee Date (if required)
		if (fieldGuaranteeDateDisplayed)
		{
			final Timestamp guaranteeDate = fieldGuaranteeDate.getValue();
			asi.setGuaranteeDate(guaranteeDate);
			if (as.isGuaranteeDate() && as.isGuaranteeDateMandatory() && guaranteeDate == null)
			{
				mandatory.add(msgBL.translate(getCtx(), I_M_AttributeSetInstance.COLUMNNAME_GuaranteeDate));
			}
			changed = true;
		}

		//
		// New Instance
		if (changed || asi.getM_AttributeSetInstance_ID() <= 0)
		{
			InterfaceWrapperHelper.save(asi);
			changed = true;
		}
		final int attributeSetInstanceId = asi.getM_AttributeSetInstance_ID();

		//
		// Save Instance Attributes
		for (final MAttribute attribute : m_attributes)
		{
			final CEditor editor = attributeId2editor.get(attribute.getM_Attribute_ID());

			if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
			{
				@SuppressWarnings("unchecked")
				final CComboBox<I_M_AttributeValue> editorCombo = (CComboBox<I_M_AttributeValue>)editor;
				final I_M_AttributeValue attributeValue = editorCombo.getSelectedItem();
				if (attribute.isMandatory() && attributeValue == null)
				{
					mandatory.add(attribute.getName());
				}
				attribute.setMAttributeInstance(attributeSetInstanceId, attributeValue);
			}
			else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attribute.getAttributeValueType()))
			{
				final VNumber editorNumber = (VNumber)editor;
				final BigDecimal value = (BigDecimal)editorNumber.getValue();
				if (attribute.isMandatory() && value == null)
				{
					mandatory.add(attribute.getName());
				}
				attribute.setMAttributeInstance(attributeSetInstanceId, value);
			}
			else if (MAttribute.ATTRIBUTEVALUETYPE_Date.equals(attribute.getAttributeValueType()))
			{
				final VDate editorDate = (VDate)editor;
				final Timestamp value = editorDate.getValue();
				if (attribute.isMandatory() && value == null)
				{
					mandatory.add(attribute.getName());
				}
				attribute.setMAttributeInstance(attributeSetInstanceId, value);
			}
			else
			{
				final VString editorString = (VString)editor;
				final String value = editorString.getText();
				if (attribute.isMandatory() && Check.isEmpty(value, false))
				{
					mandatory.add(attribute.getName());
				}
				attribute.setMAttributeInstance(attributeSetInstanceId, value);
			}
			changed = true;
		}	// for all attributes

		//
		// Throw exception if there are some mandatory fields which were not set
		if (!mandatory.isEmpty())
		{
			throw new AdempiereException("@FillMandatory@ " + StringUtils.toString(mandatory, ", "));
		}

		// Save Model
		if (changed)
		{
			asi.setMAttributeSet(as); // NOTE: this is workaround for the case when M_AttributeSet_ID=0
			attributeSetInstanceBL.setDescription(asi);
			InterfaceWrapperHelper.save(asi);
		}

		return asi;
	}

	/**************************************************************************
	 * Get Instance ID
	 *
	 * @return Instance ID
	 */
	public int getM_AttributeSetInstance_ID()
	{
		return asiEdited == null ? -1 : asiEdited.getM_AttributeSetInstance_ID();
	} // getM_AttributeSetInstance_ID

	/**
	 * Get Instance Name
	 *
	 * @return Instance Name
	 */
	public String getM_AttributeSetInstanceName()
	{
		return asiEdited == null ? null : asiEdited.getDescription();
	}

	/**
	 * Get Locator ID
	 *
	 * @return M_Locator_ID
	 */
	public int getM_Locator_ID()
	{
		return m_M_Locator_ID;
	}

	/**
	 * Returns true if user selected/edited the ASI and he/she pressed OK (did not canceled).
	 *
	 * @return true if changed
	 */
	public boolean isChanged()
	{
		return asiEdited != null;
	}

} // VPAttributeDialog
