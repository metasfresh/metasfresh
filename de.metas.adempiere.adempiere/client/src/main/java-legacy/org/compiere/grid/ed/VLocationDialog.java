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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.I_C_Country;
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
import org.compiere.swing.ListComboBoxModel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import com.akunagroup.uk.postcode.AddressInterface;
import com.akunagroup.uk.postcode.AddressLookupInterface;

import de.metas.adempiere.form.IClientUI;

/**
 * Dialog to enter Location Info (Address)
 *
 * @author Jorg Janke
 * @version $Id: VLocationDialog.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1831060 ] Location dialog should use Address1, Address2 ... elements
 * @author Michael Judd, Akuna Ltd (UK) <li>FR [ 1741222 ] - Webservice connector for address lookups
 * @author Cristina Ghita, www.arhipac.ro <li>FR [ 2794312 ] Location AutoComplete
 */
public class VLocationDialog extends CDialog
		implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6952838437136830975L;

	private final int m_WindowNo;

	/**
	 * Constructor
	 *
	 * @param parentFrame parent
	 * @param title title (field name)
	 * @param location Model Location
	 */
	public VLocationDialog(final Frame parentFrame, final String title, final MLocation location)
	{
		super(parentFrame, title, true);
		m_WindowNo = Env.getWindowNo(parentFrame);

		final Properties ctx = Env.getCtx();

		try
		{
			jbInit();
		}
		catch (final Exception ex)
		{
			log.error("init failed", ex);
		}

		//
		// Location (model)
		_location = location;
		if (_location == null)
		{
			_location = new MLocation(ctx, 0, ITrx.TRXNAME_None);
		}

		// Overwrite title
		if (_location.getC_Location_ID() <= 0)
		{
			setTitle(msgBL.getMsg(ctx, "LocationNew"));
		}
		else
		{
			setTitle(msgBL.getMsg(ctx, "LocationUpdate"));
		}

		// Reset TAB_INFO context
		setContext(CTXNAME_C_Region_ID, null);
		setContext(CTXNAME_C_Country_ID, null);

		//
		// Country
		MCountry.setDisplayLanguage(Env.getAD_Language(ctx));
		fCountryModel.set(MCountry.getCountries(ctx));

		//
		// Add listeners
		fCountry.addActionListener(this);
		fOnline.addActionListener(this);
		fRegion.addActionListener(this);

		loadFieldsFromLocationModel();

		AEnv.positionCenterWindow(parentFrame, this);
	}

	// services
	private final static transient Logger log = LogManager.getLogger(VLocationDialog.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String CTXNAME_C_Region_ID = "C_Region_ID";
	private static final String CTXNAME_C_Country_ID = "C_Country_ID";

	private boolean m_change = false;
	private MLocation _location;

	private final CPanel mainPanel = new CPanel();
	//
	private final CTextField fAddress1 = new CTextField(20); // length=60
	private final CTextField fAddress2 = new CTextField(20); // length=60
	private final CTextField fAddress3 = new CTextField(20); // length=60
	private final CTextField fAddress4 = new CTextField(20); // length=60
	private final CTextField fCity = new CTextField(20); // length=60
	private final CityAutoCompleter fCityAutoCompleter = new CityAutoCompleter(fCity)
	{
		@Override
		protected void onCurrentCityChanged(final CityVO city, final CityVO cityOld)
		{
			VLocationDialog.this.onCityChanged(city);
		};
	};
	private final ListComboBoxModel<MCountry> fCountryModel = new ListComboBoxModel<>();
	private final CComboBox<MCountry> fCountry = new CComboBox<>(fCountryModel);
	private final RegionsComboBoxModel fRegionModel = new RegionsComboBoxModel();
	private final CComboBox<MRegion> fRegion = new CComboBox<>(fRegionModel);
	private final CTextField fPostal = new CTextField(5); // length=10
	private final CTextField fPostalAdd = new CTextField(5); // length=10
	private final CButton fOnline = new CButton(msgBL.getMsg(Env.getCtx(), "Online"));
	private final List<LocationPart> locationParts = new ArrayList<>();
	private LocationPart locationPart_Online;
	private LocationPart locationPart_Region;

	private static final LocationCaptureSequence DEFAULT_CaptureSequence = LocationCaptureSequence.fromString("CO");	// we use country only to allow user to change the country at least
	private LocationCaptureSequence _captureSequence = DEFAULT_CaptureSequence;

	/**
	 * Static component init
	 *
	 * @throws Exception
	 */
	void jbInit() throws Exception
	{
		// NOTE: because we have problems with window resizing when some address fields are added,
		// we are setting the minimum size to make sure there is room for all address fields, even if they are not displayed
		setMinimumSize(new Dimension(370, 330));

		//
		// Content pane
		final CPanel contentPane = new CPanel();
		{
			final BorderLayout panelLayout = new BorderLayout();
			panelLayout.setHgap(5);
			panelLayout.setVgap(10);
			contentPane.setLayout(panelLayout);
			setContentPane(contentPane);
		}

		//
		// Center: address fields
		{
			final MigLayout mainPanelLayout = new MigLayout(
					// layoutConstraints
					new LC()
							.fillX()
							.hideMode(3)
					// colConstraints
					, new AC()
							.index(0).grow(50).fill()
							.index(1).grow(100).fill()
					// rowConstraints
					, new AC()
					);
			mainPanel.setLayout(mainPanelLayout);
			contentPane.add(mainPanel, BorderLayout.CENTER);
			//

			fPostal.addFocusListener(new FocusAdapter()
			{
				@Override
				public void focusLost(final FocusEvent e)
				{
					final String text = fPostal.getText();
					fPostal.setText(text == null ? null : text.trim());
				}
			});

			createAndAddLocationPart(LocationCaptureSequence.PART_Address1, "Address1", fAddress1);
			createAndAddLocationPart(LocationCaptureSequence.PART_Address1, "Address2", fAddress2);
			createAndAddLocationPart(LocationCaptureSequence.PART_Address1, "Address3", fAddress3);
			createAndAddLocationPart(LocationCaptureSequence.PART_Address1, "Address4", fAddress4);
			createAndAddLocationPart(LocationCaptureSequence.PART_City, "City", fCity);
			createAndAddLocationPart(LocationCaptureSequence.PART_Postal, "Postal", fPostal);
			createAndAddLocationPart(LocationCaptureSequence.PART_PostalAdd, "PostalAdd", fPostalAdd);
			locationPart_Region = createAndAddLocationPart(LocationCaptureSequence.PART_Country, "Region", fRegion);
			locationPart_Region.setEnabledCustom(false);
			createAndAddLocationPart(LocationCaptureSequence.PART_Country, "Country", fCountry);
			locationPart_Online = createAndAddLocationPart(LocationCaptureSequence.PART_Country, "Online", fOnline);
			locationPart_Online.setEnabledCustom(false);
		}

		//
		// Bottom panel: confirm panel
		{
			final CPanel southPanel = new CPanel();
			southPanel.setLayout(new BorderLayout());
			contentPane.add(southPanel, BorderLayout.SOUTH);

			final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
			// Make the buttons not focusable to make it easy for user when he/she is tabbing between location fields
			confirmPanel.getOKButton().setFocusable(false);
			confirmPanel.getCancelButton().setFocusable(false);
			//
			confirmPanel.setActionListener(this);
			southPanel.add(confirmPanel, BorderLayout.NORTH);
		}
	}

	private final void setLocationCaptureSequenceFromCountry(final I_C_Country country)
	{
		final LocationCaptureSequence captureSequence;
		final String captureSequenceStr = country == null ? null : country.getCaptureSequence();
		if (Check.isEmpty(captureSequenceStr, true))
		{
			log.error("CaptureSequence empty for " + country + ". Using default: " + DEFAULT_CaptureSequence);
			captureSequence = DEFAULT_CaptureSequence;
		}
		else
		{
			captureSequence = LocationCaptureSequence.fromString(captureSequenceStr);
		}

		setLocationCaptureSequence(captureSequence);
	}

	private final void setLocationCaptureSequence(final LocationCaptureSequence captureSequence)
	{
		Check.assumeNotNull(captureSequence, "captureSequence not null");

		// Do nothing if the capture sequence was not changed
		if (Check.equals(_captureSequence, captureSequence))
		{
			return;
		}

		for (final LocationPart locationPart : locationParts)
		{
			locationPart.setEnabledByCaptureSequence(captureSequence);
		}

		//
		// Update UI
		invalidate();
		revalidate();

		// Remember current capture sequence
		_captureSequence = captureSequence;
	}

	private LocationCaptureSequence getLocationCaptureSequence()
	{
		return _captureSequence == null ? DEFAULT_CaptureSequence : _captureSequence;
	}

	private final void loadFieldsFromLocationModel()
	{
		final MLocation location = getC_Location();

		fAddress1.setText(location.getAddress1());
		fAddress2.setText(location.getAddress2());
		fAddress3.setText(location.getAddress3());
		fAddress4.setText(location.getAddress4());

		fCountry.setSelectedItem(location.getCountry());

		final I_C_Region region = location.getRegion();
		fRegion.setSelectedItem(region);

		final int cityId = location.getC_City_ID();
		final String cityName = location.getCity();
		final int regionId = region == null ? -1 : region.getC_Region_ID();
		final String regionName = region == null ? null : region.getName();
		final CityVO city = new CityVO(cityId, cityName, regionId, regionName);
		fCityAutoCompleter.setItemNoPopup(city);

		fPostal.setText(location.getPostal());
		fPostalAdd.setText(location.getPostal_Add());
	}

	private final void setContext(final String name, final Integer valueInt)
	{
		final String valueStr = valueInt == null ? null : String.valueOf(valueInt);
		Env.setContext(Env.getCtx(), m_WindowNo, Env.TAB_INFO, name, valueStr);
	}

	private LocationPart createAndAddLocationPart(final String partName, final String label, final JComponent field)
	{
		final LocationPart locationPart = new LocationPart(partName, label, field);

		mainPanel.add(locationPart.getLabel());
		mainPanel.add(locationPart.getField(), new CC().wrap());

		locationParts.add(locationPart);

		return locationPart;
	}

	@Override
	public void dispose()
	{
		if (!m_change && _location != null && !_location.is_new())
		{
			_location = new MLocation(_location.getCtx(), _location.get_ID(), ITrx.TRXNAME_None);
		}
		super.dispose();
	}

	/**
	 * ActionListener
	 *
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			clientUI.error(m_WindowNo, ex);
		}
	}

	private void actionPerformed0(final ActionEvent e) throws Exception
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			action_OK();
			m_change = true;
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			m_change = false;
			dispose();
		}

		// Country Changed - display in new Format
		else if (e.getSource() == fCountry)
		{
			onCountryChanged();
		}
		//
		// Region Changed
		else if (e.getSource() == fRegion)
		{
			onRegionChanged();
		}
		else if (e.getSource() == fOnline)
		{
			// check to see if we have a postcode lookup plugin for this country
			final MCountry country = fCountry.getSelectedItem();
			if (country != null && country.isPostcodeLookup())
			{
				lookupPostcode(country, fCity.getText(), fPostal.getText());
			}
		}
	}

	private final void onCountryChanged()
	{
		final MCountry country = fCountry.getSelectedItem();
		if (country != null)
		{
			setLocationCaptureSequenceFromCountry(country);
			fRegionModel.setCountry(country);
			locationPart_Region.setEnabledCustom(country.isHasRegion());
			locationPart_Region.setLabelText(fRegionModel.getRegionNameLabel());
			locationPart_Online.setEnabledCustom(country.isPostcodeLookup());

			fCityAutoCompleter.setC_Country_ID(country.getC_Country_ID());
			setContext(CTXNAME_C_Country_ID, country.getC_Country_ID());
		}
	}

	private final void onRegionChanged()
	{
		final int selectedRegionId = fRegionModel.getSelectedRegionId();
		fCityAutoCompleter.setC_Region_ID(selectedRegionId);
		setContext(CTXNAME_C_Region_ID, selectedRegionId);
	}

	private final void onCityChanged(final CityVO cityVO)
	{
		if (cityVO != null && cityVO.getC_Region_ID() > 0)
		{
			fRegionModel.setSelectedRegionId(cityVO.getC_Region_ID());
		}
	}

	private void validate_OK()
	{
		final LocationCaptureSequence captureSequence = getLocationCaptureSequence();

		final List<String> fields = new ArrayList<>();
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_Address1) && fAddress1.getText().trim().length() == 0)
		{
			fields.add("Address1");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_Address2) && fAddress2.getText().trim().length() == 0)
		{
			fields.add("Address2");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_Address3) && fAddress3.getText().trim().length() == 0)
		{
			fields.add("Address3");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_Address4) && fAddress4.getText().trim().length() == 0)
		{
			fields.add("Address4");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_City) && fCity.getText().trim().length() == 0)
		{
			fields.add("C_City_ID");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_Region) && fRegion.getSelectedItem() == null)
		{
			fields.add("C_Region_ID");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_Postal) && fPostal.getText().trim().length() == 0)
		{
			fields.add("Postal");
		}
		if (captureSequence.isPartMandatory(LocationCaptureSequence.PART_PostalAdd) && fPostalAdd.getText().trim().length() == 0)
		{
			fields.add("PostalAdd");
		}

		if (!fields.isEmpty())
		{
			throw new FillMandatoryException(false, fields);
		}
	}

	/**
	 * OK - check for changes (save them) & Exit
	 */
	private void action_OK()
	{
		validate_OK();

		final MLocation m_location = getC_Location();
		m_location.setAddress1(fAddress1.getText());
		m_location.setAddress2(fAddress2.getText());
		m_location.setAddress3(fAddress3.getText());
		m_location.setAddress4(fAddress4.getText());
		m_location.setCity(fCity.getText());
		m_location.setC_City_ID(fCityAutoCompleter.getC_City_ID());
		m_location.setPostal(fPostal.getText());
		m_location.setPostal_Add(fPostalAdd.getText());

		// Country/Region
		final MCountry country = fCountry.getSelectedItem();
		m_location.setCountry(country);
		final MRegion region = fRegion.getSelectedItem();
		if (m_location.getCountry().isHasRegion() && region != null)
		{
			m_location.setRegion(region);
		}
		else
		{
			m_location.setRegion(null);
			m_location.setRegionName(null);
		}

		// Save changes
		InterfaceWrapperHelper.save(m_location);
	}	// actionOK

	/**
	 * Get result
	 *
	 * @return true, if changed
	 */
	public boolean isChanged()
	{
		return m_change;
	}	// getChange

	/**
	 * Get edited Value (MLocation)
	 *
	 * @return location
	 */
	public MLocation getValue()
	{
		return getC_Location();
	}	// getValue

	private final MLocation getC_Location()
	{
		return _location;
	}

	/**
	 * lookupPostcode
	 *
	 *
	 * @param country
	 * @param postcode
	 * @return
	 */
	private String lookupPostcode(final MCountry country, final String city, String postcode)
	{
		// Initialize the lookup class.
		AddressLookupInterface pcLookup = null;
		try
		{
			final AddressLookupInterface pcLookupTmp = (AddressLookupInterface)Class
					.forName(country.getLookupClassName()).newInstance();
			pcLookup = pcLookupTmp.newInstance();
			pcLookup.setCity(city); // metas
			pcLookup.setCountryCode(country.getCountryCode()); // metas
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		// remove any spaces from the postcode and convert to upper case
		postcode = postcode.replaceAll(" ", "").toUpperCase();
		log.debug("Looking up postcode: " + postcode);

		// Lookup postcode on server.
		pcLookup.setServerUrl(country.getLookupUrl());
		pcLookup.setClientID(country.getLookupClientID());
		pcLookup.setPassword(country.getLookupPassword());
		final int result = pcLookup.lookupPostcode(postcode);
		if (result >= 1)
		{
			// Success
			fillLocation(pcLookup.getAddressData(), country);
			// FIXME fAddress1.requestFocusInWindow();
		}
		// metas: changed
		else if (result == 0)
		{
			if (pcLookup.isRegisterLocalSupported())
			{
				final String msg = msgBL.getMsg(Env.getCtx(), "PostalCodeNotFound_Add", new Object[] { postcode, city });
				if (clientUI.ask(m_WindowNo, "NotFound", msg))
				{
					pcLookup.registerLocal(postcode);
				}
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
	 * Fills the location field using the information retrieved from postcode servers.
	 *
	 * @param ctx Context
	 * @param pkeyData Lookup results
	 * @param windowNo Window No.
	 * @param tab Tab
	 * @param field Field
	 */
	// metas: tsa: us786: fully changed this method because initial implementation was a huge crap
	private void fillLocation(final Map<String, Object> postcodeData, final MCountry country)
	{
		if (postcodeData == null || postcodeData.isEmpty())
		{
			return;
		}

		final List<AddressInterface> addresses = new ArrayList<AddressInterface>();
		for (final Object o : postcodeData.values())
		{
			addresses.add((AddressInterface)o);
		}

		final boolean userChanges = !isEmpty(fCity);
		// || !isEmpty(fAddress1)
		// || !isEmpty(fAddress2)
		// || !isEmpty(fAddress3)
		// || !isEmpty(fAddress4)

		final AddressInterface values; // selected value
		if (userChanges || addresses.size() > 1)
		{
			final JList<Object> jlistAddresses = new JList<>(addresses.toArray());
			jlistAddresses.setCellRenderer(new AddressListCellRenderer());
			jlistAddresses.setSelectedIndex(0);

			final JScrollPane scroll = new JScrollPane(jlistAddresses);
			scroll.setPreferredSize(new Dimension(250, 80));

			final int response = JOptionPane.showConfirmDialog(
					this,
					new Object[] { // options
					msgBL.getMsg(Env.getCtx(), "Postal_Select_System_Data_Warning"),
							scroll,
					},
					msgBL.getMsg(Env.getCtx(), "Postal_Select_System_Data"), // title
					JOptionPane.DEFAULT_OPTION, // optionType,
					JOptionPane.INFORMATION_MESSAGE, // messageType,
					null // icon,
					);
			if (response < 0)
			{
				return;
			}
			values = (AddressInterface)jlistAddresses.getSelectedValue();
		}
		else
		{
			values = addresses.get(0);
		}

		if (values == null)
		{
			log.warn("Nothing selected");
			return;
		}
		// Overwrite the values in location field.
		// fAddress1.setText(values.getStreet1());
		// fAddress2.setText(values.getStreet2());
		// fAddress3.setText(values.getStreet3());
		// fAddress4.setText(values.getStreet4());
		fCityAutoCompleter.setTextNoPopup(values.getCity());
		fPostal.setText(values.getPostcode());

		// Do region lookup
		if (country.isHasRegion())
		{
			// get all regions for this country
			final MRegion[] regions = MRegion.getRegions(country.getCtx(), country.getC_Country_ID());

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
						log.debug("Found region: " + regions[i].getName());
						found = true;
					}
				}
				if (!found)
				{
					// add new region
					final MRegion region = new MRegion(country, values.getRegion());
					if (region.save())
					{
						log.debug("Added new region from web service: " + values.getRegion());

						// clears cache
						// Env.reset(false); // not needed; this shall be done automatically on save

						// reload regions to combo box
						fRegionModel.set(MRegion.getRegions(Env.getCtx(), country.getC_Country_ID()));
						// select region
						fRegion.setSelectedItem(values);
					}
					else
					{
						log.error("Error saving new region: " + region.getName());
					}
				}
			}
			else
			{
				log.error("Region lookup failed for Country: " + country.getName());
			}
		}
	}

	// metas: begin ------------------------------------------------------------
	private static final boolean isEmpty(final JTextField field)
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
		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
		{
			if (value instanceof AddressInterface)
			{
				final AddressInterface address = (AddressInterface)value;
				return super.getListCellRendererComponent(list, toString(address), index, isSelected, cellHasFocus);
			}
			else
			{
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		}

		private static String toString(final AddressInterface address)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(address.getPostcode());
			sb.append(" - ");
			sb.append(address.getCountryCode());
			if (!Check.isEmpty(address.getRegion(), true))
			{
				sb.append(", ").append(address.getRegion());
			}
			if (!Check.isEmpty(address.getCity(), true))
			{
				sb.append(", ").append(address.getCity());
			}
			if (!Check.isEmpty(address.getStreet1(), true))
			{
				sb.append(", ").append(address.getStreet1());
			}
			if (!Check.isEmpty(address.getStreet2(), true))
			{
				sb.append(", ").append(address.getStreet2());
			}
			if (!Check.isEmpty(address.getStreet3(), true))
			{
				sb.append(", ").append(address.getStreet3());
			}
			if (!Check.isEmpty(address.getStreet4(), true))
			{
				sb.append(", ").append(address.getStreet4());
			}
			return sb.toString();
		}
	}

	private static final class RegionsComboBoxModel extends ListComboBoxModel<MRegion>
	{
		private static final long serialVersionUID = 1L;

		private final String defaultRegionNameLabel;

		private Integer countryId = null;
		private String regionNameLabel = null;
		private boolean hasRegions = false;

		public RegionsComboBoxModel()
		{
			super();

			defaultRegionNameLabel = Services.get(IMsgBL.class).translate(Env.getCtx(), "Region");

			regionNameLabel = defaultRegionNameLabel;
		}

		public void setCountry(final MCountry country)
		{
			final int countryId = country.getC_Country_ID();

			//
			// Do nothing if country has not changed
			if (this.countryId != null && this.countryId == countryId)
			{
				return;
			}

			final boolean hasRegion = country.isHasRegion();

			//
			// Get region name of currency country
			String regionNameLabel;
			if (hasRegion)
			{
				regionNameLabel = country.get_Translation(I_C_Country.COLUMNNAME_RegionName);
				if (Check.isEmpty(regionNameLabel, true))
				{
					regionNameLabel = defaultRegionNameLabel;
				}
			}
			else
			{
				regionNameLabel = defaultRegionNameLabel;
			}

			//
			// Set current country (not avoid loading it again for same input)
			this.countryId = countryId;
			this.regionNameLabel = regionNameLabel;
			hasRegions = hasRegion;

			//
			// Load regions of current country
			if (hasRegion)
			{
				set(MRegion.getRegions(Env.getCtx(), countryId));
			}
			else
			{
				clear();
			}
		}

		public String getRegionNameLabel()
		{
			return regionNameLabel;
		}

		@SuppressWarnings("unused")
		public boolean isHasRegions()
		{
			return hasRegions;
		}

		public int getSelectedRegionId()
		{
			final MRegion region = getSelectedItem();
			if (region == null)
			{
				return -1;
			}
			return region.getC_Region_ID();
		}
		
		public void setSelectedRegionId(final int regionId)
		{
			if(regionId <= 0)
			{
				setSelectedItem(null);
				return;
			}
			
			for (int i = 0, size = getSize(); i < size; i++)
			{
				final MRegion region = getElementAt(i);
				if(region != null && region.getC_Region_ID() == regionId)
				{
					setSelectedItem(region);
				}
			}
		}
	}

	/**
	 * Small class used to handle the components of a location part (e.g. the label and field of Address1)
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private final class LocationPart
	{
		private final JLabel label;
		private final JComponent field;
		private final String partName;

		private boolean enabledCustom = true;
		private boolean enabledByCaptureSequence = false;

		public LocationPart(final String partName, final String label, final JComponent field)
		{
			super();
			this.partName = partName;

			this.field = field;
			this.field.setName(label);

			this.label = new CLabel(Check.isEmpty(label) ? "" : msgBL.translate(Env.getCtx(), label));
			this.label.setHorizontalAlignment(SwingConstants.RIGHT);
			this.label.setLabelFor(field);

			update();
		}

		public void setEnabledCustom(final boolean enabledCustom)
		{
			if (this.enabledCustom == enabledCustom)
			{
				return;
			}

			this.enabledCustom = enabledCustom;
			update();
		}

		public void setEnabledByCaptureSequence(final LocationCaptureSequence captureSequence)
		{
			final boolean enabled = captureSequence.hasPart(partName);
			setEnabledByCaptureSequence(enabled);
		}

		private void setEnabledByCaptureSequence(final boolean enabledByCaptureSequence)
		{
			if (this.enabledByCaptureSequence == enabledByCaptureSequence)
			{
				return;
			}

			this.enabledByCaptureSequence = enabledByCaptureSequence;
			update();
		}

		private final void update()
		{
			final boolean enabled = enabledCustom && enabledByCaptureSequence;
			label.setEnabled(enabled);
			label.setVisible(enabled);
			field.setEnabled(enabled);
			field.setVisible(enabled);
		}

		public void setLabelText(final String labelText)
		{
			label.setText(labelText);
		}

		public JLabel getLabel()
		{
			return label;
		}

		public JComponent getField()
		{
			return field;
		}
	}
}	// VLocationDialog
