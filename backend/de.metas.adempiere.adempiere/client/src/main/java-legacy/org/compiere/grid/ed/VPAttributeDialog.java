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

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.form.IClientUI;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.AWindow;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.PAttributeInstance;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
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
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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
	private final I_M_AttributeSetInstance _asiTemplate;
	private final List<I_M_Attribute> _availableAttributes;
	private final boolean _allowSelectExistingASI;

	private I_M_AttributeSetInstance asiEdited = null;
	private int _locatorId;
	private final ProductId _productId;
	private final int _callerColumnId;

	/** Row Counter */
	private int m_row = 0;
	/** List of Editors */
	private Map<Integer, CEditor> attributeId2editor = new HashMap<>();
	/** Editing attributes */
	private List<I_M_Attribute> editorAttributes = new ArrayList<>();
	/** Length of Instance value (40) */
	private static final int INSTANCE_VALUE_LENGTH = 40;

	private CButton bSelectExistingASI = new CButton(Images.getImageIcon2("PAttribute16"));

	private CMenuItem mZoom;

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

		_productId = attributeContext.getProductId();
		_callerColumnId = asiInfo.getCallerColumnId();


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

	private I_M_AttributeSetInstance getASITemplate()
	{
		return _asiTemplate;
	}

	private MAttributeInstance getAITemplate(final I_M_Attribute attribute)
	{
		final I_M_AttributeSetInstance asiTemplate = getASITemplate();
		if (asiTemplate == null)
		{
			return null;
		}

		final MAttribute attributePO = LegacyAdapters.convertToPO(attribute);
		return attributePO.getMAttributeInstance(asiTemplate.getM_AttributeSetInstance_ID());
	}

	private ProductId getProductId()
	{
		return _productId;
	}

	private boolean isAllowSelectExistingASI()
	{
		return _allowSelectExistingASI;
	}

	private int getCallerColumnId()
	{
		return _callerColumnId;
	}

	/**
	 * Initialize all panel fields and editors based on {@link #getASITemplate()}.
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
		for (final I_M_Attribute attribute : getAvailableAttributes())
		{
			addAttributeLine(attribute);
		}

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

	private List<I_M_Attribute> getAvailableAttributes()
	{
		return _availableAttributes;
	}

	/**
	 * Add Attribute Line
	 *
	 * @param attribute attribute
	 */
	private void addAttributeLine(final I_M_Attribute attribute)
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

			final MAttribute attributePO = LegacyAdapters.convertToPO(attribute);
			final List<UIAttributeListValue> values = UIAttributeListValue.toList(attributePO.getMAttributeValues(getSOTrx()));	// optional = null
			final CComboBox<UIAttributeListValue> editor = new CComboBox<>(values);
			boolean found = false;
			if (instance != null && instance.getM_AttributeValue_ID() > 0)
			{
				for (int i = 0; i < values.size(); i++)
				{
					final UIAttributeListValue value = values.get(i);
					if (value != null && value.getRepoId() == instance.getM_AttributeValue_ID())
					{
						editor.setSelectedIndex(i);
						found = true;
						break;
					}
				}
				if (found)
				{
					log.debug("Attribute=" + attribute.getName() + " #" + values.size() + " - found: " + instance);
				}
				else
				{
					log.warn("Attribute=" + attribute.getName() + " #" + values.size() + " - NOT found: " + instance);
				}
			}	// setComboBox
			else
			{
				log.debug("Attribute=" + attribute.getName() + " #" + values.size() + " no instance");
			}
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
			{
				editor.setEnabled(false);
			}
			else
			{
				attributeId2editor.put(attributeId, editor);
			}
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
			{
				editor.setEnabled(false);
			}
			else
			{
				attributeId2editor.put(attributeId, editor);
			}
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
			{
				editor.setValue(instance.getValueDate());
			}
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
			{
				editor.setEnabled(false);
			}
			else
			{
				attributeId2editor.put(attributeId, editor);
			}
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
			{
				editor.setText(instance.getValue());
			}
			label.setLabelFor(editor);
			centerPanel.add(editor, null);
			if (readOnly)
			{
				editor.setEnabled(false);
			}
			else
			{
				attributeId2editor.put(attributeId, editor);
			}
		}

		//
		// Add our attribute to the list of editing attributes
		editorAttributes.add(attribute);
	}	// addAttributeLine

	private SOTrx getSOTrx()
	{
		return attributeContext.getSoTrx();
	}

	/**
	 * Sets the given result (which will be returned to caller) and dispose this dialog.
	 *
	 * @param asi
	 * @param M_Locator_ID
	 */
	private final void setResultAndDispose(final I_M_AttributeSetInstance asi, int M_Locator_ID)
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

		// OK
		else if (event.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			final I_M_AttributeSetInstance asi = saveSelection();
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

		WarehouseId warehouseId = attributeContext.getWarehouseId();

		final DocTypeId docTypeId = attributeContext.getDocTypeId();
		if (docTypeId != null)
		{
			final I_C_DocType docType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId);
			String docBaseType = docType.getDocBaseType();
			if (MDocType.DOCBASETYPE_MaterialReceipt.equals(docBaseType))
			{
				warehouseId = null;
			}
		}

		// teo_sarca [ 1564520 ] Inventory Move: can't select existing attributes
		int M_Locator_ID = 0;
		if (getCallerColumnId() == 8551) // TODO: hardcoded: M_MovementLine[324].M_AttributeSetInstance_ID[8551]
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
			pstmt.setInt(1, getProductId().getRepoId());
			pstmt.setInt(2, M_Locator_ID <= 0 ? WarehouseId.toRepoId(warehouseId) : M_Locator_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				title = rs.getString(1) + " - " + rs.getString(2);
				warehouseId = WarehouseId.ofRepoIdOrNull(rs.getInt(3)); // fetch the actual warehouse - teo_sarca [ 1564520 ]
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
		final BPartnerId bpartnerId = attributeContext.getBpartnerId();
		final PAttributeInstance pai = new PAttributeInstance(this, title, warehouseId, M_Locator_ID, getProductId(), bpartnerId);
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
		MQuery zoomQuery = new MQuery("M_Lot");
		zoomQuery.addRestriction("M_Lot_ID", Operator.EQUAL, M_Lot_ID);
		log.info(zoomQuery.toString());
		//
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//
		AdWindowId AD_Window_ID = AdWindowId.ofRepoId(257);		// Lot
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
	private I_M_AttributeSetInstance saveSelection()
	{
		final IMutable<I_M_AttributeSetInstance> asiRef = new Mutable<>();
		trxManager.runInNewTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_M_AttributeSetInstance asi = saveSelection0();
				asiRef.setValue(asi);
			}
		});

		return asiRef.getValue();
	}

	private I_M_AttributeSetInstance saveSelection0()
	{
		log.info("");

		final MAttributeSet attributeSet = getM_AttributeSet();
		final I_M_AttributeSetInstance asiTemplate2 = getASITemplate();

		// Create a new ASI which is copying the existing one

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class, PlainContextAware.newWithThreadInheritedTrx(getCtx()));
		if (asiTemplate2 != null)
		{
			InterfaceWrapperHelper.copyValues(asiTemplate2, asi, false); // honorIsCalculated=false => copy everything
		}
		asi.setM_AttributeSet(attributeSet); // make sure we have the right AttributeSet model set

		//
		boolean changed = false;
		final Set<String> mandatory = new LinkedHashSet<>();

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
		for (final I_M_Attribute attribute : editorAttributes)
		{
			final MAttribute attributePO = LegacyAdapters.convertToPO(attribute);
			final CEditor editor = attributeId2editor.get(attribute.getM_Attribute_ID());

			if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
			{
				@SuppressWarnings("unchecked")
				final CComboBox<UIAttributeListValue> editorCombo = (CComboBox<UIAttributeListValue>)editor;
				final UIAttributeListValue uiAttributeValue = editorCombo.getSelectedItem();
				if (attribute.isMandatory() && uiAttributeValue == null)
				{
					mandatory.add(attribute.getName());
				}
				attributePO.setMAttributeInstance(attributeSetInstanceId, uiAttributeValue.getAttributeListValue());
			}
			else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attribute.getAttributeValueType()))
			{
				final VNumber editorNumber = (VNumber)editor;
				final BigDecimal value = (BigDecimal)editorNumber.getValue();
				if (attribute.isMandatory() && value == null)
				{
					mandatory.add(attribute.getName());
				}
				attributePO.setMAttributeInstance(attributeSetInstanceId, value);
			}
			else if (MAttribute.ATTRIBUTEVALUETYPE_Date.equals(attribute.getAttributeValueType()))
			{
				final VDate editorDate = (VDate)editor;
				final Timestamp value = editorDate.getValue();
				if (attribute.isMandatory() && value == null)
				{
					mandatory.add(attribute.getName());
				}
				attributePO.setMAttributeInstance(attributeSetInstanceId, value);
			}
			else
			{
				final VString editorString = (VString)editor;
				final String value = editorString.getText();
				if (attribute.isMandatory() && Check.isEmpty(value, true))
				{
					mandatory.add(attribute.getName());
				}
				attributePO.setMAttributeInstance(attributeSetInstanceId, value);
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
			final MAttributeSetInstance asiPO = LegacyAdapters.convertToPO(asi);
			asiPO.setMAttributeSet(attributeSet); // NOTE: this is workaround for the case when M_AttributeSet_ID=0
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
	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return AttributeSetInstanceId.ofRepoIdOrNull(asiEdited == null ? -1 : asiEdited.getM_AttributeSetInstance_ID());
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

	@Value
	private static class UIAttributeListValue
	{
		public static List<UIAttributeListValue> toList(final AttributeListValue[] array)
		{
			return Stream.of(array).map(UIAttributeListValue::new).collect(ImmutableList.toImmutableList());
		}

		AttributeListValue attributeListValue;
		String displayName;

		private UIAttributeListValue(final AttributeListValue attributeListValue)
		{
			this.attributeListValue = attributeListValue;
			displayName = attributeListValue != null ? attributeListValue.getName() : "";
		}

		@Override
		public String toString()
		{
			return displayName;
		}
		
		public int getRepoId()
		{
			return getAttributeListValue().getId().getRepoId();
		}

	}
} // VPAttributeDialog
