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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.PAttributeInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.X_M_MovementLine;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.product.IProductPA;
import de.metas.product.ProductId;
import de.metas.product.impl.ProductPA;

/**
 * Product Attribute Set Product/Instance Dialog Editor. Called from
 * VPAttribute.actionPerformed<br/> Change by metas: if a serial number is
 * entered and if there already exists an asi with that serno (for the same
 * product, but regardless of location).
 * 
 * @author Jorg Janke
 * @author t.schoeneberg@metas.de
 */
public class VPAttributeSerNoDialog extends CDialog
	implements ActionListener, IAttributeDialog
{
	private static final long serialVersionUID = -6934188428984105495L;

	public static final String MSG_DUPLICAT_SERIAL_NO = "PAttributeDAILOG.DuplicateSerNo";
	
	/**
	 *	Product Attribute Instance Dialog
	 *	@param frame parent frame
	 *	@param M_AttributeSetInstance_ID Product Attribute Set Instance id
	 * 	@param M_Product_ID Product id
	 * 	@param C_BPartner_ID b partner
	 * 	@param productWindow this is the product window (define Product Instance)
	 * 	@param AD_Column_ID column
	 * 	@param WindowNo window
	 */
	public VPAttributeSerNoDialog (Frame frame, int M_AttributeSetInstance_ID, 
		int M_Product_ID, int C_BPartner_ID, 
		 int AD_Column_ID, int WindowNo)
	{
		super (frame, Msg.translate(Env.getCtx(), "M_AttributeSetInstance_ID") , true);
		log.info("M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID 
			+ ", M_Product_ID=" + M_Product_ID
			+ ", C_BPartner_ID=" + C_BPartner_ID
			+ ", Column=" + AD_Column_ID);
		m_WindowNo = Env.createWindowNo (this);
		m_M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		m_M_Product_ID = M_Product_ID;
		m_C_BPartner_ID = C_BPartner_ID;
		m_AD_Column_ID = AD_Column_ID;
		m_WindowNoParent = WindowNo;

		try
		{
			jbInit();
		}
		catch(Exception ex)
		{
			log.error("VPAttributeDialog" + ex);
		}
		//	Dynamic Init
		if (!initAttributes ())
		{
			dispose();
			return;
		}
		AEnv.showCenterWindow(frame, this);
	}	//	VPAttributeDialog

	private int						m_WindowNo;
	private MAttributeSetInstance	m_masi;
	private int 					m_M_AttributeSetInstance_ID;
	private int 					m_M_Locator_ID;
	private String					m_M_AttributeSetInstanceName;
	private int 					m_M_Product_ID;
	private int						m_C_BPartner_ID;
	private int						m_AD_Column_ID;
	private int						m_WindowNoParent;

	/**	Change							*/
	private boolean					m_changed = false;
	
	private Logger					log = LogManager.getLogger(getClass());
	/** Row Counter					*/
	private int						m_row = 0;


	private CButton bSelect = new CButton(Images.getImageIcon2("PAttribute16"));

	//	Ser No
	private VString fieldSerNo = new VString ("SerNo", false, false, true, 20, 20, null, null);
		
	//
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel centerPanel = new CPanel();
	private ALayout centerLayout = new ALayout(5,5, true);
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	// metas
	private IProductPA productService = new ProductPA();
	
	/**
	 *	Layout
	 * 	@throws Exception
	 */
	private void jbInit () throws Exception
	{
		this.getContentPane().setLayout(mainLayout);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		centerPanel.setLayout(centerLayout);
		//
		confirmPanel.setActionListener(this);
	}	//	jbInit

	/**
	 *	Dyanmic Init.
	 *  @return true if initialized
	 */
	private boolean initAttributes ()
	{
		if (m_M_Product_ID == 0)
			return false;
		
		MAttributeSet as = null;
		
		if (m_M_Product_ID != 0)
		{
			//	Get Model
			m_masi = MAttributeSetInstance.get(Env.getCtx(), m_M_AttributeSetInstance_ID, m_M_Product_ID);
			if (m_masi == null)
			{
				log.error("No Model for M_AttributeSetInstance_ID=" + m_M_AttributeSetInstance_ID + ", M_Product_ID=" + m_M_Product_ID);
				return false;
			}
			Env.setContext(Env.getCtx(), m_WindowNo, "M_AttributeSet_ID", m_masi.getM_AttributeSet_ID());
	
			//	Get Attribute Set
			as = m_masi.getMAttributeSet();
		}
		else 
		{
			int M_AttributeSet_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, "M_AttributeSet_ID");
			m_masi = new MAttributeSetInstance (Env.getCtx(), 0, M_AttributeSet_ID, null);
			as = m_masi.getMAttributeSet();
		}
		
		//	Product has no Attribute Set
		if (as == null)		
		{
			ADialog.error(m_WindowNo, this, "PAttributeNoAttributeSet");
			return false;
		}
		//	Product has no Instance Attributes
		if (!as.isInstanceAttribute())
		{
			ADialog.error(m_WindowNo, this, "PAttributeNoInstanceAttribute");
			return false;
		}

		if(!as.isSerNo()){
			//TODO
			throw new IllegalArgumentException();
		}
		final String movementType = Env.getContext(Env.getCtx(),m_WindowNoParent,I_M_InOut.COLUMNNAME_MovementType);
		final boolean enterNewSerialNo = movementType.endsWith("+");
		
		if(!enterNewSerialNo){

			//we set up this dialog to allow only the
			// selection of a given serial number
			
			bSelect.setText(Msg.translate(Env.getCtx(), "SerNo"));
			bSelect.addActionListener(this);
			centerPanel.add(bSelect, null);
			
		} else {
		
			CLabel label = new CLabel (Msg.translate(Env.getCtx(), "SerNo"));
			label.setLabelFor(fieldSerNo);
			centerPanel.add(label, new ALayoutConstraint(m_row++,0));
		}
	
		fieldSerNo.setText(m_masi.getSerNo());
		fieldSerNo.setEditable(enterNewSerialNo);
				
		centerPanel.add(fieldSerNo, null);

		//	Window usually to wide (??)
		Dimension dd = centerPanel.getPreferredSize();
		dd.width = Math.min(500, dd.width);
		centerPanel.setPreferredSize(dd);
		return true;
	}	//	initAttribute

/**
	 *	dispose
	 */
	@Override
	public void dispose()
	{
		removeAll();
		Env.clearWinContext(m_WindowNo);
		//
		Env.setContext(Env.getCtx(), Env.WINDOW_INFO, Env.TAB_INFO, "M_AttributeSetInstance_ID", 
			String.valueOf(m_M_AttributeSetInstance_ID));
		Env.setContext(Env.getCtx(), Env.WINDOW_INFO, Env.TAB_INFO, "M_Locator_ID", 
			String.valueOf(m_M_Locator_ID));
		//
		super.dispose();
	}	//	dispose

	/**
	 *	ActionListener
	 *  @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//	Select Instance
		if (e.getSource() == bSelect)
		{
			if (cmd_select())
				dispose();
		}
		
		//	OK
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			if (saveSelection())
				dispose();
		}
		//	Cancel
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			m_changed = false;
			m_M_AttributeSetInstance_ID = 0;
			m_M_Locator_ID = 0;
			dispose();
		}
		else
			log.error("not found - " + e);
	}	//	actionPerformed

	/**
	 * 	Instance Selection Button
	 * 	@return true if selected
	 */
	private boolean cmd_select()
	{
		log.info("");
		
		int M_Warehouse_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, "M_Warehouse_ID");
		
		int C_DocType_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, "C_DocType_ID");
		if (C_DocType_ID > 0) {
			MDocType doctype = new MDocType (Env.getCtx(), C_DocType_ID, null);
			String docbase = doctype.getDocBaseType();
			if (docbase.equals(MDocType.DOCBASETYPE_MaterialReceipt))
				M_Warehouse_ID = 0;
		}
		
		// teo_sarca [ 1564520 ] Inventory Move: can't select existing attributes
		int M_Locator_ID = 0;
		if (m_AD_Column_ID == 8551) { // TODO: hardcoded: M_MovementLine[324].M_AttributeSetInstance_ID[8551]
			M_Locator_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, X_M_MovementLine.COLUMNNAME_M_Locator_ID, true); // only window
		}
		
		String title = "";
		//	Get Text
		String sql = "SELECT p.Name, w.Name, w.M_Warehouse_ID FROM M_Product p, M_Warehouse w "
			+ "WHERE p.M_Product_ID=? AND w.M_Warehouse_ID"
				+ (M_Locator_ID <= 0 ? "=?" : " IN (SELECT M_Warehouse_ID FROM M_Locator where M_Locator_ID=?)"); // teo_sarca [ 1564520 ]
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, M_Locator_ID <= 0 ? M_Warehouse_ID : M_Locator_ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
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
			rs = null; pstmt = null;
		}
		//		
		PAttributeInstance pai = new PAttributeInstance(
				this,
				title,
				WarehouseId.ofRepoIdOrNull(M_Warehouse_ID),
				M_Locator_ID,
				ProductId.ofRepoIdOrNull(m_M_Product_ID),
				BPartnerId.ofRepoIdOrNull(m_C_BPartner_ID));
		if (pai.getM_AttributeSetInstance_ID() != -1)
		{
			m_M_AttributeSetInstance_ID = pai.getM_AttributeSetInstance_ID();
			m_M_AttributeSetInstanceName = pai.getM_AttributeSetInstanceName();
			m_M_Locator_ID = pai.getM_Locator_ID();
			m_changed = true;
			return true;
		}
		return false;
	}	//	cmd_select

	/**
	 *	Save Selection
	 *	@return true if saved
	 */
	private boolean saveSelection()
	{
		MAttributeSet as = m_masi.getMAttributeSet();
		if (as == null)
			return true;
		//
		String mandatory = "";

		log.debug("SerNo=" + fieldSerNo.getText());
		String enteredSerNo  = fieldSerNo.getText();
		String oldSerNo= m_masi.getSerNo(); 
		
		if(enteredSerNo == null){
			enteredSerNo = "";
		}
		if(oldSerNo == null){
			oldSerNo = "";
		}	
		
		m_changed = !enteredSerNo.equals(oldSerNo);
		
		if (!m_changed) {
			return true;
		}
		
		// metas: search for an existing serial number and issue an error if
		// one is found
		
		final Collection<MAttributeSetInstance> existingSerNos = productService
				.retrieveSerno(m_M_Product_ID, enteredSerNo, null);
		if (!existingSerNos.isEmpty()) {
			ADialog.error(m_WindowNo, this, MSG_DUPLICAT_SERIAL_NO);
			return false;
		}
		// metas end

		m_masi.setSerNo(enteredSerNo);
		if (as.isSerNoMandatory() && (enteredSerNo == null || enteredSerNo.length() == 0))
			mandatory += " - " + Msg.translate(Env.getCtx(), "SerNo");
		m_changed = true;
		
		//	***	Save Attributes ***
		//	New Instance
		if (m_changed || m_masi.getM_AttributeSetInstance_ID() == 0)
		{
			InterfaceWrapperHelper.save(m_masi);
			m_M_AttributeSetInstance_ID = m_masi.getM_AttributeSetInstance_ID ();
			m_M_AttributeSetInstanceName = m_masi.getDescription();
		}
		
		//	Save Model
		if (m_changed)
		{
			// NOTE: actual Description will be set on save
			m_masi.setDescription("");
			InterfaceWrapperHelper.save(m_masi);
		}
		m_M_AttributeSetInstance_ID = m_masi.getM_AttributeSetInstance_ID ();
		m_M_AttributeSetInstanceName = m_masi.getDescription();
		//
		if (mandatory.length() > 0)
		{
			ADialog.error(m_WindowNo, this, "FillMandatory", mandatory);
			return false;
		}
		return true;
	}	//	saveSelection

	
	/**************************************************************************
	 * 	Get Instance ID
	 * 	@return Instance ID
	 */
	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return m_M_AttributeSetInstance_ID;
	}	//	getM_AttributeSetInstance_ID

	/**
	 * 	Get Instance Name
	 * 	@return Instance Name
	 */
	@Override
	public String getM_AttributeSetInstanceName()
	{
		return m_M_AttributeSetInstanceName;
	}	//	getM_AttributeSetInstanceName
	
	/**
	 * Get Locator ID
	 * @return M_Locator_ID
	 */
	@Override
	public int getM_Locator_ID()
	{
		return m_M_Locator_ID; 
	}

	/**
	 * 	Value Changed
	 *	@return true if changed
	 */
	@Override
	public boolean isChanged()
	{
		return m_changed;
	}	//	isChanged

} //	VPAttributeDialog
