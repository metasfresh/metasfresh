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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.I_C_Region;
import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MRegion;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.akunagroup.uk.postcode.AddressInterface;
import com.akunagroup.uk.postcode.AddressLookupInterface;

/**
 *	Dialog to enter Location Info (Address)
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VLocationDialog.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1831060 ] Location dialog should use Address1, Address2 ... elements
 * @author Michael Judd, Akuna Ltd (UK)
 * 			<li>FR [ 1741222 ] - Webservice connector for address lookups
 * @author Cristina Ghita, www.arhipac.ro
 * 			<li>FR [ 2794312 ] Location AutoComplete
 */
public class VLocationDialog extends CDialog 
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6952838437136830975L;

	// metas: commented: not used
//	/** Lookup result header */
//	private Object[] header = null;

	private final int m_WindowNo = 0;

	/**
	 *	Constructor
	 *
	 * @param frame parent
	 * @param title title (field name)
	 * @param location Model Location
	 */
	public VLocationDialog (Frame frame, String title, MLocation location)
	{
		super(frame, title, true);
		//m_WindowNo = WindowNo;
		try
		{
			jbInit();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, ex.getMessage());
		}
		m_location = location;
		if (m_location == null)
			m_location = new MLocation (Env.getCtx(), 0, ITrx.TRXNAME_None);
		//	Overwrite title	
		if (m_location.getC_Location_ID() == 0)
			setTitle(Msg.getMsg(Env.getCtx(), "LocationNew"));
		else
			setTitle(Msg.getMsg(Env.getCtx(), "LocationUpdate"));

		// Reset TAB_INFO context
		Env.setContext(Env.getCtx(), m_WindowNo, Env.TAB_INFO, "C_Region_ID", null);
		Env.setContext(Env.getCtx(), m_WindowNo, Env.TAB_INFO, "C_Country_ID", null);

		//	Current Country
		MCountry.setDisplayLanguage(Env.getAD_Language(Env.getCtx()));
		fCountry = new CComboBox<>(MCountry.getCountries(Env.getCtx()));
		fCountry.setSelectedItem(m_location.getCountry());
		m_origCountry_ID = m_location.getC_Country_ID();
		//	Current Region
		fRegion = new CComboBox<>(MRegion.getRegions(Env.getCtx(), m_origCountry_ID));
		if (m_location.getCountry().isHasRegion())
		{
			if (m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName) != null
					&& m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName).trim().length() > 0)
				lRegion.setText(m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName));
			else
				lRegion.setText(Msg.getMsg(Env.getCtx(), "Region"));
		}
		fRegion.setSelectedItem(m_location.getRegion());
		//
		fOnline.setText(Msg.getMsg(Env.getCtx(), "Online"));
		initLocation();
		fCountry.addActionListener(this);
		fOnline.addActionListener(this);
		fRegion.addActionListener(this);
		AEnv.positionCenterWindow(frame, this);
	}	//	VLocationDialog

	private boolean 	m_change = false;
	private MLocation	m_location;
	private final int m_origCountry_ID;
	private int			s_oldCountry_ID = 0;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VLocationDialog.class);

	private final CPanel panel = new CPanel();
	private final CPanel mainPanel = new CPanel();
	private final CPanel southPanel = new CPanel();
	private final BorderLayout panelLayout = new BorderLayout();
	private final GridBagLayout gridBagLayout = new GridBagLayout();
	private final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private final BorderLayout southLayout = new BorderLayout();
	//
	private final CLabel lAddress1 = new CLabel(Msg.getElement(Env.getCtx(), "Address1"));
	private final CLabel lAddress2 = new CLabel(Msg.getElement(Env.getCtx(), "Address2"));
	private final CLabel lAddress3 = new CLabel(Msg.getElement(Env.getCtx(), "Address3"));
	private final CLabel lAddress4 = new CLabel(Msg.getElement(Env.getCtx(), "Address4"));
	private final CLabel lCity = new CLabel(Msg.getMsg(Env.getCtx(), "City"));
	private final CLabel lCountry = new CLabel(Msg.getMsg(Env.getCtx(), "Country"));
	private final CLabel lRegion = new CLabel(Msg.getMsg(Env.getCtx(), "Region"));
	private final CLabel lPostal = new CLabel(Msg.getMsg(Env.getCtx(), "Postal"));
	private final CLabel lPostalAdd = new CLabel(Msg.getMsg(Env.getCtx(), "PostalAdd"));
	private final CLabel lOnline = new CLabel("");
	private final CTextField fAddress1 = new CTextField(20); // length=60
	private final CTextField fAddress2 = new CTextField(20); // length=60
	private final CTextField fAddress3 = new CTextField(20); // length=60
	private final CTextField fAddress4 = new CTextField(20); // length=60
	private final CTextField fCity = new CTextField(20); // length=60
	private CityAutoCompleter	fCityAutoCompleter;
	private final CComboBox<MCountry> fCountry;
	private CComboBox<MRegion> fRegion;
	private final CTextField fPostal = new CTextField(5); // length=10
	private final CTextField fPostalAdd = new CTextField(5); // length=10
	private final CButton fOnline = new CButton();
	//
	private final GridBagConstraints gbc = new GridBagConstraints();
	private final Insets labelInsets = new Insets(2, 15, 2, 0); // top,left,bottom,right
	private final Insets fieldInsets = new Insets(2, 5, 2, 10);

	private boolean isCityMandatory = false;
	private boolean isRegionMandatory = false;
	private boolean isAddress1Mandatory = false;
	private boolean isAddress2Mandatory = false;
	private boolean isAddress3Mandatory = false;
	private boolean isAddress4Mandatory = false;
	private boolean isPostalMandatory = false;
	private boolean isPostalAddMandatory = false;

	private boolean inCountryAction;
	private boolean inOKAction;

	/**
	 *	Static component init
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		panel.setLayout(panelLayout);
		southPanel.setLayout(southLayout);
		mainPanel.setLayout(gridBagLayout);
		panelLayout.setHgap(5);
		panelLayout.setVgap(10);
		getContentPane().add(panel);
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(confirmPanel, BorderLayout.NORTH);
		//
		confirmPanel.setActionListener(this);
		//
		fCityAutoCompleter = new CityAutoCompleter(fCity);
		
		// metas: begin
		fPostal.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				String text = fPostal.getText();
				fPostal.setText(text == null ? null : text.trim());
			}
		});
		// metas: end
	}	//	jbInit

	/**
	 *	Dynamic Init & fill fields - Called when Country or Region changes
	 */
	private void initLocation()
	{
		final MCountry country = m_location.getCountry();
		log.fine(country.getName() + ", Region=" + country.isHasRegion() + " " + country.getCaptureSequence() + ", C_Location_ID=" + m_location.getC_Location_ID());
		
		//	new Country
		if (country.getC_Country_ID() != s_oldCountry_ID)
		{
			fRegion.removeAllItems();
			if (country.isHasRegion())
			{
				for (MRegion region : MRegion.getRegions(Env.getCtx(), country.getC_Country_ID()))
				{
				    fRegion.addItem(region);
				}
				if (m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName) != null
						&& m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName).trim().length() > 0)
					lRegion.setText(m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName));
				else
					lRegion.setText(Msg.getMsg(Env.getCtx(), "Region"));
			}
			s_oldCountry_ID = m_location.getC_Country_ID();
		}
		
		if (m_location.getC_Region_ID() > 0 && m_location.getC_Region().getC_Country_ID() == country.getC_Country_ID())
		{
			fRegion.setSelectedItem(m_location.getC_Region());
		}
		else
		{
			fRegion.setSelectedItem(null);
			m_location.setC_Region_ID(0);
		}

		if (country.isHasRegion() && m_location.getC_Region_ID() > 0)
		{
			fCityAutoCompleter.setC_Region_ID(m_location.getC_Region_ID());
			Env.setContext(Env.getCtx(), m_WindowNo, Env.TAB_INFO, "C_Region_ID", String.valueOf(m_location.getC_Region_ID()));
		}
		else
		{
			fCityAutoCompleter.setC_Region_ID(-1);
			Env.setContext(Env.getCtx(), m_WindowNo, Env.TAB_INFO, "C_Region_ID", "0");
		}
		fCityAutoCompleter.setC_Country_ID(country.getC_Country_ID());
		Env.setContext(Env.getCtx(), m_WindowNo, Env.TAB_INFO, "C_Country_ID", String.valueOf(country.get_ID()));
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridy = 0;			//	line
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.insets = fieldInsets;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.weighty = 0;
		
		mainPanel.removeAll();

		mainPanel.add(Box.createVerticalStrut(5), gbc);    	//	top gap

		int line = 1;
		//  sequence of City Postal Region - @P@ @C@ - @C@, @R@ @P@
		String ds = country.getCaptureSequence();
		if (ds == null || ds.length() == 0)
		{
			log.log(Level.SEVERE, "CaptureSequence empty - " + country);
			ds = "";	//	@C@,  @P@
		}
		isCityMandatory = false;
		isRegionMandatory = false;
		isAddress1Mandatory = false;
		isAddress2Mandatory = false;
		isAddress3Mandatory = false;
		isAddress4Mandatory = false;
		isPostalMandatory = false;
		isPostalAddMandatory = false;
		StringTokenizer st = new StringTokenizer(ds, "@", false);
		while (st.hasMoreTokens())
		{
			String s = st.nextToken();
			if (s.startsWith("CO")) {
				//  Country Last
				addLine(line++, lCountry, fCountry);
				// disable online if this country doesn't have post code lookup
				if (m_location.getCountry().isPostcodeLookup()) {
					addLine(line++, lOnline, fOnline);
				}
			} else if (s.startsWith("A1")) {
				addLine(line++, lAddress1, fAddress1);
				isAddress1Mandatory = s.endsWith("!");
			} else if (s.startsWith("A2")) {
				addLine(line++, lAddress2, fAddress2);
				isAddress2Mandatory = s.endsWith("!");
			} else if (s.startsWith("A3")) {
				addLine(line++, lAddress3, fAddress3);
				isAddress3Mandatory = s.endsWith("!");
			} else if (s.startsWith("A4")) {
				addLine(line++, lAddress4, fAddress4);
				isAddress4Mandatory = s.endsWith("!");
			} else if (s.startsWith("C")) {
				addLine(line++, lCity, fCity);
				isCityMandatory = s.endsWith("!");
			} else if (s.startsWith("P")) {
				addLine(line++, lPostal, fPostal);
				isPostalMandatory = s.endsWith("!");
			} else if (s.startsWith("A")) {
				addLine(line++, lPostalAdd, fPostalAdd);
				isPostalAddMandatory = s.endsWith("!");
			} else if (s.startsWith("R") && m_location.getCountry().isHasRegion()) {
				addLine(line++, lRegion, fRegion);
				isRegionMandatory = s.endsWith("!");
			}
		}
		
		//	Fill it
		if (m_location.getC_Location_ID() > 0)
		{
			fAddress1.setText(m_location.getAddress1());
			fAddress2.setText(m_location.getAddress2());
			fAddress3.setText(m_location.getAddress3());
			fAddress4.setText(m_location.getAddress4());
			
			final int cityId = m_location.getC_City_ID();
			final String cityName = m_location.getCity();
			final I_C_Region region = m_location.getRegion();
			final int regionId = region == null ? -1 : region.getC_Region_ID();
			final String regionName = region == null ? null : region.getName();
			final CityVO city = new CityVO(cityId, cityName, regionId, regionName);
			fCityAutoCompleter.setItemNoPopup(city);
			
			fPostal.setText(m_location.getPostal());
			fPostalAdd.setText(m_location.getPostal_Add());
			
			if (m_location.getCountry().isHasRegion())
			{
				if (m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName) != null
						&& m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName).trim().length() > 0)
					lRegion.setText(m_location.getCountry().get_Translation(MCountry.COLUMNNAME_RegionName));
				else
					lRegion.setText(Msg.getMsg(Env.getCtx(), "Region"));
				fRegion.setSelectedItem(m_location.getRegion());
			}
			if (!fCountry.getSelectedItem().equals(country))
				fCountry.setSelectedItem(country);
		}
		//	Update UI
		pack();
	}	//	initLocation

	/**
	 *	Add Line to screen
	 *
	 *  @param line line number (zero based)
	 *  @param label label
	 *  @param field field
	 */
	private void addLine(int line, JLabel label, JComponent field)
	{
		gbc.gridy = line;
		//	label
		gbc.insets = labelInsets;
		gbc.gridx = 0;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		mainPanel.add(label, gbc);
		//	Field
		gbc.insets = fieldInsets;
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = fieldInsets;
		mainPanel.add(field, gbc);
		
	}	//	addLine

	@Override
	public void dispose()
	{
		if (!m_change && m_location != null && !m_location.is_new())
		{
			m_location = new MLocation(m_location.getCtx(), m_location.get_ID(), null);
		}	
		super.dispose();
	}

	/**
	 *	ActionListener
	 *  @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			inOKAction = true;
			
			if (m_location.getCountry().isHasRegion() && fRegion.getSelectedItem() == null)
			{
				if (fCityAutoCompleter.getC_Region_ID() > 0 && fCityAutoCompleter.getC_Region_ID() != m_location.getC_Region_ID())
				{
					final MRegion region = MRegion.get(Env.getCtx(), fCityAutoCompleter.getC_Region_ID());
					fRegion.setSelectedItem(region);
					m_location.setRegion(region);
				}
			}
			
			String msg = validate_OK();
			if (msg != null) {
				ADialog.error(0, this, "FillMandatory", Msg.parseTranslation(Env.getCtx(), msg));
				inOKAction = false;
				return;
			}

			if (action_OK())
			{
				m_change = true;
				dispose();
			}
			else
			{
				// metas: tsa: retrieve last error instead of always displaying "CityNotFound" error 
				String errmsg = CLogger.retrieveErrorString("CityNotFound");
				ADialog.error(m_WindowNo, this, "Error", errmsg);
			}
			inOKAction = false;
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			m_change = false;
			dispose();
		}

		//	Country Changed - display in new Format
		else if (e.getSource() == fCountry)
		{
			inCountryAction = true;
			//	Modifier for Mouse selection is 16  - for any key selection 0
			MCountry c = fCountry.getSelectedItem();
			m_location.setCountry(c);

			initLocation();
			fCountry.requestFocus();	//	allows to use Keyboard selection
			inCountryAction = false;
		}
		//		Region Changed 
		else if (e.getSource() == fRegion)
		{
			if (inCountryAction || inOKAction)
				return;
			MRegion r = fRegion.getSelectedItem();
			m_location.setRegion(r);
			m_location.setC_City_ID(0);
			m_location.setCity(null);

			initLocation();
			fRegion.requestFocus();	//	allows to use Keyboard selection
		}
		else if (e.getSource() == fOnline)
		{
			
			// check to see if we have a postcode lookup plugin for this country
			MCountry c = fCountry.getSelectedItem();
			if (c.isPostcodeLookup())
			{
				// metas: tsa: wrap it to a try/catch block to catch and display errors
				try
				{
					lookupPostcode(c, fCity.getText(), fPostal.getText());
				}
				catch (Exception ex)
				{
					ADialog.error(0, this, "Error", ex.getLocalizedMessage());
				}
			}
		}
	}	//	actionPerformed

	// LCO - address 1, region and city required
	private String validate_OK() {
		String fields = "";
		if (isAddress1Mandatory && fAddress1.getText().trim().length() == 0) {
			fields = fields + " " + "@Address1@, ";
		}
		if (isAddress2Mandatory && fAddress2.getText().trim().length() == 0) {
			fields = fields + " " + "@Address2@, ";
		}
		if (isAddress3Mandatory && fAddress3.getText().trim().length() == 0) {
			fields = fields + " " + "@Address3@, ";
		}
		if (isAddress4Mandatory && fAddress4.getText().trim().length() == 0) {
			fields = fields + " " + "@Address4@, ";
		}
		if (isCityMandatory && fCity.getText().trim().length() == 0) {
			fields = fields + " " + "@C_City_ID@, ";
		}
		if (isRegionMandatory && fRegion.getSelectedItem() == null) {
			fields = fields + " " + "@C_Region_ID@, ";
		}
		if (isPostalMandatory && fPostal.getText().trim().length() == 0) {
			fields = fields + " " + "@Postal@, ";
		}
		if (isPostalAddMandatory && fPostalAdd.getText().trim().length() == 0) {
			fields = fields + " " + "@PostalAdd@, ";
		}
		
		if (fields.trim().length() > 0)
			return fields.substring(0, fields.length() -2);

		return null;
	}

	/**
	 * 	OK - check for changes (save them) & Exit
	 */
	private boolean action_OK()
	{
		m_location.setAddress1(fAddress1.getText());
		m_location.setAddress2(fAddress2.getText());
		m_location.setAddress3(fAddress3.getText());
		m_location.setAddress4(fAddress4.getText());
		m_location.setCity(fCity.getText());
		m_location.setC_City_ID(fCityAutoCompleter.getC_City_ID());
		m_location.setPostal(fPostal.getText());
		m_location.setPostal_Add(fPostalAdd.getText());
		//  Country/Region
		MCountry c = fCountry.getSelectedItem();
		m_location.setCountry(c);
		if (m_location.getCountry().isHasRegion())
		{
			MRegion r = fRegion.getSelectedItem();
			m_location.setRegion(r);
		}
		else
			m_location.setC_Region_ID(0);
		//	Save changes
		if(m_location.save())
		{
			return true;
		}
		else
		{
			return false;
		}
	}	//	actionOK

	/**
	 *	Get result
	 *  @return true, if changed
	 */
	public boolean isChanged()
	{
		return m_change;
	}	//	getChange

	/**
	 * 	Get edited Value (MLocation)
	 *	@return location
	 */
	public MLocation getValue()
	{
		return m_location;
	}	//	getValue
	/**
	 * lookupPostcode
	 * 
	 * 
	 * @param country
	 * @param postcode
	 * @return
	 */
	private String lookupPostcode(MCountry country, String city, String postcode)
	{
		// Initialize the lookup class.
		AddressLookupInterface pcLookup = null;
		try {
			AddressLookupInterface pcLookupTmp = (AddressLookupInterface) Class
						.forName(country.getLookupClassName()).newInstance();
			pcLookup = pcLookupTmp.newInstance();
			pcLookup.setCity(city); // metas
			pcLookup.setCountryCode(country.getCountryCode()); // metas
		} catch (Exception e) {
			// metas: changed
			throw new AdempiereException(e);
			//e.printStackTrace();
			//return "lookupAddress(): " + e.getMessage();
		}
		
		// remove any spaces from the postcode and convert to upper case
		postcode = postcode.replaceAll(" ", "").toUpperCase();
		log.fine("Looking up postcode: " + postcode);
		
		// Lookup postcode on server.
		pcLookup.setServerUrl(country.getLookupUrl());
		pcLookup.setClientID(country.getLookupClientID());
		pcLookup.setPassword(country.getLookupPassword());
		int result = pcLookup.lookupPostcode(postcode); 
		if (result >= 1)
		{
			// Success
			fillLocation(pcLookup.getAddressData(), country);
			fAddress1.requestFocusInWindow();
		}
		// metas: changed
		else if (result == 0)
		{
			if (pcLookup.isRegisterLocalSupported())
			{
				final String msg = Msg.getMsg(Env.getCtx(), "PostalCodeNotFound_Add", new Object[] { postcode, city });
				if (ADialog.ask(m_WindowNo, this, "NotFound", msg))
					pcLookup.registerLocal(postcode);
			}
			else
			{
				throw new AdempiereException("@NotFound@ @Postal@");
			}
		}
		else
		{
			throw new AdempiereException("Postcode Lookup Error");
		}
		
		return "";
	}

	/**
	 * Fills the location field using the information retrieved from postcode
	 * servers.
	 * 
	 * @param ctx
	 *            Context
	 * @param pkeyData
	 *            Lookup results
	 * @param windowNo
	 *            Window No.
	 * @param tab
	 *            Tab
	 * @param field
	 *            Field
	 */
	// metas: tsa: us786: fully changed this method because initial implementation was a huge crap
	private void fillLocation(HashMap<String, Object> postcodeData, MCountry country)
	{
		if (postcodeData == null || postcodeData.isEmpty())
		{
			return;
		}
		
		final List<AddressInterface> addresses = new ArrayList<AddressInterface>();
		for (Object o : postcodeData.values())
		{
			addresses.add((AddressInterface)o);
		}
		
		boolean userChanges = !isEmpty(fCity);
//			|| !isEmpty(fAddress1)
//			|| !isEmpty(fAddress2)
//			|| !isEmpty(fAddress3)
//			|| !isEmpty(fAddress4)

		final AddressInterface values; // selected value
		if (userChanges || addresses.size() > 1)
		{
			JList<Object> jlistAddresses = new JList<>(addresses.toArray());
			jlistAddresses.setCellRenderer(new AddressListCellRenderer());
			jlistAddresses.setSelectedIndex(0);
			
			JScrollPane scroll = new JScrollPane(jlistAddresses);
			scroll.setPreferredSize(new Dimension(250, 80));
			
			final int response = JOptionPane.showConfirmDialog(
					this,
					new Object[] { // options
							Msg.getMsg(Env.getCtx(), "Postal_Select_System_Data_Warning"),
							scroll,
					},
					Msg.getMsg(Env.getCtx(), "Postal_Select_System_Data"), // title
					JOptionPane.DEFAULT_OPTION, //optionType,
					JOptionPane.INFORMATION_MESSAGE, //messageType,
					null //icon,
			);
			if (response < 0)
				return;
			values = (AddressInterface)jlistAddresses.getSelectedValue();
		}
		else
		{
			values = addresses.get(0);
		}
		
		if (values == null)
		{
			log.warning("Nothing selected");
			return;
		}
		// Overwrite the values in location field.
//		fAddress1.setText(values.getStreet1());
//		fAddress2.setText(values.getStreet2());
//		fAddress3.setText(values.getStreet3());
//		fAddress4.setText(values.getStreet4());
		fCityAutoCompleter.setTextNoPopup(values.getCity());
		fPostal.setText(values.getPostcode());

		// Do region lookup
		if (country.isHasRegion())
		{
			// get all regions for this country
			MRegion[] regions = MRegion.getRegions(country.getCtx(), country.getC_Country_ID());

			// If regions were loaded
			if (regions.length > 0)
			{
				// loop through regions array to attempt a region match - don't finish loop if region found
				boolean found = false;
				for (int i = 0; i < regions.length && !found; i++)
				{

					if (regions[i].getName().equals(values.getRegion()))
					{
						// found Region
						fRegion.setSelectedItem(regions[i]);
						log.fine("Found region: " + regions[i].getName());
						found = true;
					}
				}
				if (!found)
				{
					// add new region
					MRegion region = new MRegion(country, values.getRegion());
					if (region.save())
					{
						log.fine("Added new region from web service: " + values.getRegion());
						// clears cache
						Env.reset(false);
						// reload regions to combo box
						fRegion = new CComboBox(MRegion.getRegions(Env.getCtx(), country.getC_Country_ID()));
						// select region
						fRegion.setSelectedItem(values);
					}
					else
						log.severe("Error saving new region: " + region.getName());
				}
			}
			else
				log.severe("Region lookup failed for Country: " + country.getName());
		}
	}

// metas: begin ------------------------------------------------------------
	private boolean isEmpty(JTextField field)
	{
		return field == null || Check.isEmpty(field.getText());
	}
	
	private static class AddressListCellRenderer extends DefaultListCellRenderer
	{
		private static final long serialVersionUID = -235724306927853784L;
		
		public AddressListCellRenderer()
		{
			super();
		}
		

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			if (value instanceof AddressInterface)
			{
				AddressInterface address = (AddressInterface)value;
				return super.getListCellRendererComponent(list, toString(address), index, isSelected, cellHasFocus);
			}
			else
			{
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		}
		
		private static String toString (AddressInterface address)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(address.getPostcode());
			sb.append(" - ");
			sb.append(address.getCountryCode());
			if (!Util.isEmpty(address.getRegion(), true))
				sb.append(", ").append(address.getRegion());
			if (!Util.isEmpty(address.getCity(), true))
				sb.append(", ").append(address.getCity());
			if (!Util.isEmpty(address.getStreet1(), true))
				sb.append(", ").append(address.getStreet1());
			if (!Util.isEmpty(address.getStreet2(), true))
				sb.append(", ").append(address.getStreet2());
			if (!Util.isEmpty(address.getStreet3(), true))
				sb.append(", ").append(address.getStreet3());
			if (!Util.isEmpty(address.getStreet4(), true))
				sb.append(", ").append(address.getStreet4());
			return sb.toString();
		}
	}
}	//	VLocationDialog
