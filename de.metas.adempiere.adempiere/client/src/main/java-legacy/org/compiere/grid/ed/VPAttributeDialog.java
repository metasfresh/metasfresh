/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
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
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Lot;
import org.compiere.model.I_M_LotCtl;
import org.compiere.model.I_M_SerNoCtl;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CEditor;
import org.compiere.swing.CLabel;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;

/**
 * Product Attribute Set Product/Instance Dialog Editor.
 * Called from VPAttribute.actionPerformed
 *
 * @author Jorg Janke
 * @version $Id: VPAttributeDialog.java,v 1.4 2006/07/30 00:51:27 jjanke Exp $
 */
@SuppressWarnings("serial")
public class VPAttributeDialog extends CDialog implements ActionListener
{
	// services
	private final transient Logger log = LogManager.getLogger(getClass());
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);

	// private static final String COLUMNNAME_ASILastUpdated = "ASILastUpdated";

	/**
	 * VPAttributeDialog's context.
	 * NOTE: this is not the parent window No
	 */
	private final int m_WindowNo;
	private final IVPAttributeContext attributeContext;
	private final MAttributeSet _attributeSet;
	private final MAttributeSetInstance _asiTemplate;
	private final List<MAttribute> _availableAttributes;
	private final boolean _allowSelectExistingASI;

	private MAttributeSetInstance asiEdited = null;
	private int _locatorId;
	private final int _productId;
	private final int _adColumnId;

	/** Row Counter */
	private int m_row = 0;
	/** List of Editors */
	private Map<Integer, CEditor> attributeId2editor = new HashMap<>();
	/** Editing attributes */
	private List<MAttribute> editorAttributes = new ArrayList<>();
	/** Length of Instance value (40) */
	private static final int INSTANCE_VALUE_LENGTH = 40;

	private CButton bSelectExistingASI = new CButton(Images.getImageIcon2("PAttribute16"));

	//
	// Lot
	private final boolean isLotEnabled;
	private final VString fieldLotString = new VString("Lot", false, false, true, 20, 20, null, null);
	private CComboBox<KeyNamePair> fieldLot = null;
	private final CButton bLot = new CButton(msgBL.getMsg(Env.getCtx(), "New"));
	// Lot Popup
	private final JPopupMenu popupMenu = new JPopupMenu();
	private CMenuItem mZoom;
	//
	// SerNo
	private final boolean isSerNoEnabled;
	private final VString fieldSerNo = new VString("SerNo", false, false, true, 20, 20, null, null);
	private final CButton bSerNo = new CButton(msgBL.getMsg(Env.getCtx(), "New"));
	//
	// GuaranteeDate
	private final boolean isGuaranteeDateEnabled;
	private final VDate fieldGuaranteeDate = new VDate("GuaranteeDate", false, false, true, DisplayType.Date, msgBL.translate(Env.getCtx(), "GuaranteeDate"));
	//
	private final CTextField fieldDescription = new CTextField(20);
	//
	private final CPanel centerPanel = new CPanel();

	public VPAttributeDialog(final Frame frame, final ASIEditingInfo asiInfo, final IVPAttributeContext attributeContext)
	{
		super(frame, Services.get(IMsgBL.class).translate(Env.getCtx(), "M_AttributeSetInstance_ID"), true);

		m_WindowNo = Env.createWindowNo(this);
		this.attributeContext = attributeContext;

		final I_M_AttributeSet attributeSet = asiInfo.getM_AttributeSet();
		_attributeSet = LegacyAdapters.convertToPO(attributeSet);
		_asiTemplate = asiInfo.getM_AttributeSetInstance();
		_availableAttributes = asiInfo.getAvailableAttributes();
		_allowSelectExistingASI = asiInfo.isAllowSelectExistingASI();

		_productId = attributeContext.getM_Product_ID();
		_adColumnId = asiInfo.getAD_Column_ID();

		this.isLotEnabled = asiInfo.isLotEnabled();
		this.isSerNoEnabled = asiInfo.isSerNoEnabled();
		this.isGuaranteeDateEnabled = asiInfo.isGuaranteeDateEnabled();

		//
		// Initialize
		try
		{
			jbInit();

			// Init all UI editors and fields based on ASI template
			initAttributes();

			AEnv.showCenterWindow(frame, this);
		}
		catch (Exception ex)
		{
			dispose();
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	/**
	 * Layout
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
		
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

	private MAttributeSet getM_AttributeSet()
	{
		return _attributeSet;
	}

	private boolean isASITemplateNew()
	{
		final I_M_AttributeSetInstance asiTemplate = getASITemplate();
		return asiTemplate == null || asiTemplate.getM_AttributeSetInstance_ID() <= 0;
	}

	private MAttributeSetInstance getASITemplate()
	{
		return _asiTemplate;
	}

	private MAttributeInstance getAITemplate(final MAttribute attribute)
	{
		final MAttributeSetInstance asiTemplate = getASITemplate();
		if (asiTemplate == null)
		{
			return null;
		}
		return attribute.getMAttributeInstance(asiTemplate.getM_AttributeSetInstance_ID());
	}

	private int getM_Product_ID()
	{
		return _productId;
	}

	private boolean isAllowSelectExistingASI()
	{
		return _allowSelectExistingASI;
	}

	private int getAD_Column_ID()
	{
		return _adColumnId;
	}

	/**
	 * Initialize all panel fields and editors based on {@link #asiTemplate}.
	 */
	private final void initAttributes()
	{
		final Properties ctx = getCtx();
		final boolean allowSelectExistingASI = isAllowSelectExistingASI();

		final MAttributeSet attributeSet = getM_AttributeSet();

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
		// Create attributes UI editors
		for (final MAttribute attribute : getAvailableAttributes())
		{
			addAttributeLine(attribute);
		}

		//
		// Lot
		if (isLotEnabled)
		{
			final I_M_AttributeSetInstance asiTemplate = getASITemplate();

			CLabel label = new CLabel(msgBL.translate(ctx, "Lot"));
			label.setLabelFor(fieldLotString);
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldLotString, null);
			fieldLotString.setText(asiTemplate == null ? null : asiTemplate.getLot());
			// M_Lot_ID
			// int AD_Column_ID = 9771; // M_AttributeSetInstance.M_Lot_ID
			// fieldLot = new VLookup ("M_Lot_ID", false,false, true,
			// MLookupFactory.get(getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.TableDir));
			final String sql = "SELECT M_Lot_ID, Name "
					+ "FROM M_Lot l "
					+ "WHERE EXISTS (SELECT M_Product_ID FROM M_Product p "
					+ "WHERE p.M_AttributeSet_ID=" + attributeSet.getM_AttributeSet_ID()
					+ " AND p.M_Product_ID=l.M_Product_ID)";
			fieldLot = new CComboBox<>(DB.getKeyNamePairs(sql, true));
			label = new CLabel(msgBL.translate(ctx, "M_Lot_ID"));
			label.setLabelFor(fieldLot);
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldLot, null);
			if (asiTemplate != null && asiTemplate.getM_Lot_ID() > 0)
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
			if (attributeSet.getM_LotCtl_ID() > 0)
			{
				if (Env.getUserRolePermissions().isTableAccess(I_M_Lot.Table_ID, false)
						&& Env.getUserRolePermissions().isTableAccess(I_M_LotCtl.Table_ID, false)
						&& !attributeSet.isExcludeLot(getAD_Column_ID(), attributeContext.isSOTrx()))
				{
					centerPanel.add(bLot, null);
					bLot.addActionListener(this);
				}
			}
			// Popup
			fieldLot.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (SwingUtilities.isRightMouseButton(e))
						popupMenu.show((Component)e.getSource(), e.getX(), e.getY());
				}
			});
			mZoom = new CMenuItem(msgBL.getMsg(ctx, "Zoom"), Images.getImageIcon2("Zoom16"));
			mZoom.addActionListener(this);
			popupMenu.add(mZoom);
		}	// Lot

		//
		// SerNo
		if (isSerNoEnabled)
		{
			final I_M_AttributeSetInstance asiTemplate = getASITemplate();

			CLabel label = new CLabel(msgBL.translate(ctx, "SerNo"));
			label.setLabelFor(fieldSerNo);
			fieldSerNo.setText(asiTemplate == null ? null : asiTemplate.getSerNo());
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldSerNo, null);
			// New SerNo Button
			if (attributeSet.getM_SerNoCtl_ID() > 0)
			{
				if (Env.getUserRolePermissions().isTableAccess(I_M_SerNoCtl.Table_ID, false)
						&& !attributeSet.isExcludeSerNo(getAD_Column_ID(), attributeContext.isSOTrx()))
				{
					centerPanel.add(bSerNo, null);
					bSerNo.addActionListener(this);
				}
			}
		}	// SerNo

		//
		// GuaranteeDate.
		if (isGuaranteeDateEnabled)
		{
			final I_M_AttributeSetInstance asiTemplate = getASITemplate();
			Date guaranteeDate = asiTemplate == null ? null : asiTemplate.getGuaranteeDate();

			CLabel label = new CLabel(msgBL.translate(ctx, "GuaranteeDate"));
			label.setLabelFor(fieldGuaranteeDate);
			if (isASITemplateNew())
			{
				if (guaranteeDate == null)
				{
					guaranteeDate = attributesBL.calculateBestBeforeDate(ctx,
							getM_Product_ID(), // product
							attributeContext.getC_BPartner_ID(), // vendor bpartner
							Env.getDate(ctx) // dateReceipt
					);
				}
				fieldGuaranteeDate.setValue(guaranteeDate);
			}
			else
			{
				fieldGuaranteeDate.setValue(guaranteeDate);
			}
			centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
			centerPanel.add(fieldGuaranteeDate, null);
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
			final I_M_AttributeSetInstance asiTemplate = getASITemplate();

			final CLabel labelDescription = new CLabel(msgBL.translate(ctx, "Description"));
			labelDescription.setLabelFor(fieldDescription);
			fieldDescription.setText(asiTemplate == null ? null : asiTemplate.getDescription());
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

	private List<MAttribute> getAvailableAttributes()
	{
		return _availableAttributes;
	}

	/**
	 * Add Attribute Line
	 *
	 * @param attribute attribute
	 * @param product product level attribute
	 * @param readOnly value is read only
	 */
	private void addAttributeLine(final MAttribute attribute)
	{
		final boolean readOnly = false;

		CLabel label = new CLabel(attribute.getName());
		if (attribute.getDescription() != null)
		{
			label.setToolTipText(attribute.getDescription());
		}

		centerPanel.add(label, new ALayoutConstraint(m_row++, 0));
		//
		final int attributeId = attribute.getM_Attribute_ID();
		final MAttributeInstance instance = getAITemplate(attribute);
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
		// Add our attribute to the list of editing attributes
		editorAttributes.add(attribute);
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
		this._locatorId = M_Locator_ID;
		dispose();
	}

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

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		try
		{
			actionPerformed0(event);
		}
		catch (Exception ex)
		{
			clientUI.warn(m_WindowNo, ex);
		}
	}

	private final void actionPerformed0(final ActionEvent event) throws Exception
	{
		// Select Instance
		if (event.getSource() == bSelectExistingASI)
		{
			cmd_select();
			return;
		}
		// Select Lot from existing
		else if (event.getSource() == fieldLot)
		{
			final KeyNamePair pp = fieldLot.getSelectedItem();
			if (pp != null && pp.getKey() > 0)
			{
				fieldLotString.setText(pp.getName());
				fieldLotString.setEditable(false);
			}
			else
			{
				fieldLotString.setEditable(true);
			}
		}
		// Create New Lot
		else if (event.getSource() == bLot)
		{
			KeyNamePair pp = getM_AttributeSet().createLot(getM_Product_ID());
			if (pp != null)
			{
				fieldLot.addItem(pp);
				fieldLot.setSelectedItem(pp);
			}
		}
		// Create New SerNo
		else if (event.getSource() == bSerNo)
		{
			final String serNo = getM_AttributeSet().createSerNo();
			fieldSerNo.setText(serNo);
		}

		// OK
		else if (event.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			final MAttributeSetInstance asi = saveSelection();
			final int M_Locator_ID = -1; // N/A
			setResultAndDispose(asi, M_Locator_ID);
			return;
		}
		// Cancel
		else if (event.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			final int M_Locator_ID = -1; // N/A
			setResultAndDispose(null, M_Locator_ID);
		}
		// Zoom M_Lot
		else if (event.getSource() == mZoom)
		{
			cmd_zoom();
		}
		else
		{
			log.warn("Unknown event: {}", event);
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
		if (getAD_Column_ID() == 8551) // TODO: hardcoded: M_MovementLine[324].M_AttributeSetInstance_ID[8551]
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
			pstmt.setInt(1, getM_Product_ID());
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
		final PAttributeInstance pai = new PAttributeInstance(this, title, M_Warehouse_ID, M_Locator_ID, getM_Product_ID(), bpartnerId);
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
		// Lot
		if (isLotEnabled)
		{
			final I_M_AttributeSetInstance asiTemplate = getASITemplate();
			final boolean isNewLot = asiTemplate == null || asiTemplate.getM_Lot_ID() <= 0;
			fieldLotString.setEditable(rw && isNewLot);
			if (fieldLot != null)
			{
				fieldLot.setReadWrite(rw);
			}
			bLot.setReadWrite(rw);
		}

		// Serial No
		if (isSerNoEnabled)
		{
			fieldSerNo.setReadWrite(rw);
			bSerNo.setReadWrite(rw);
		}

		// Guarantee Date
		if (isGuaranteeDateEnabled)
		{
			fieldGuaranteeDate.setReadWrite(rw);
		}

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
		zoomQuery.addRestriction("M_Lot_ID", Operator.EQUAL, M_Lot_ID);
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

		final MAttributeSet attributeSet = getM_AttributeSet();
		final MAttributeSetInstance asiTemplate2 = getASITemplate();

		// Create a new ASI which is copying the existing one
		final MAttributeSetInstance asi = new MAttributeSetInstance(getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		if (asiTemplate2 != null)
		{
			InterfaceWrapperHelper.copyValues(asiTemplate2, asi, false); // honorIsCalculated=false => copy everything
		}
		asi.setM_AttributeSet(attributeSet); // make sure we have the right AttributeSet model set

		//
		boolean changed = false;
		final Set<String> mandatory = new LinkedHashSet<>();

		//
		// Lot
		if (isLotEnabled)
		{
			final String text = fieldLotString.getText();
			if (attributeSet.isLotMandatory() && Check.isEmpty(text, true))
			{
				mandatory.add(msgBL.translate(getCtx(), "Lot"));
			}
			else
			{
				asi.setLot(text);
				final KeyNamePair pp = fieldLot.getSelectedItem();
				final int lotId = pp == null ? -1 : pp.getKey();
				asi.setM_Lot_ID(lotId);
				changed = true;
			}
		}
		else
		{
			asi.setLot(null);
			asi.setM_Lot(null);
		}

		//
		// Serial No
		if (isSerNoEnabled)
		{
			final String serNo = fieldSerNo.getText();
			if (attributeSet.isSerNoMandatory() && Check.isEmpty(serNo, true))
			{
				mandatory.add(msgBL.translate(getCtx(), "SerNo"));
			}
			else
			{
				asi.setSerNo(serNo);
				changed = true;
			}
		}
		else
		{
			asi.setSerNo(null);
		}

		//
		// Guarantee Date (if required)
		if (isGuaranteeDateEnabled)
		{
			final Timestamp guaranteeDate = fieldGuaranteeDate.getValue();
			if (attributeSet.isGuaranteeDate() && attributeSet.isGuaranteeDateMandatory() && guaranteeDate == null)
			{
				mandatory.add(msgBL.translate(getCtx(), I_M_AttributeSetInstance.COLUMNNAME_GuaranteeDate));
			}
			else
			{
				asi.setGuaranteeDate(guaranteeDate);
				changed = true;
			}
		}
		else
		{
			asi.setGuaranteeDate(null);
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
		for (final MAttribute attribute : editorAttributes)
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
				if (attribute.isMandatory() && Check.isEmpty(value, true))
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
			asi.setMAttributeSet(attributeSet); // NOTE: this is workaround for the case when M_AttributeSet_ID=0
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
		return _locatorId;
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
