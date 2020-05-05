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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Region;
import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MRegion;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

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
	private final List<LocationPart> locationParts = new ArrayList<>();
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
		if (Objects.equals(_captureSequence, captureSequence))
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
