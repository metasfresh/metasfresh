package org.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.compiere.model.MCountry;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.process.SvrProcess;

/**
 * Creates duplicates of the tax identified by the given C_Tax_ID for all
 * selected countries.
 * 
 * Change for 340: removed the copying of AD_Rule_ID because seems not to exist in 340.
 * 
 * @author Karsten Thiemann, kt@schaeffer-ag.de 
 * 
 */
public class DuplicateTax extends SvrProcess {

	/** id of the C_Tax */
	private int taxId;

	/** countries to create a tax for */
	private MCountry[] countries = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		taxId = getRecord_ID();
		log.debug("taxId: " + taxId);
		selectCountries();
	}

	/**
	 * Sets the selected countries from the country list.
	 * 
	 */
	private void selectCountries() {

		JButton euButton;
		JButton noneuButton;
		JPanel topPanel = new JPanel();
		final MCountry[] listData = MCountry.getCountries(Env.getCtx());
		final JList list = new JList(listData);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(list);
		topPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		topPanel.add(dataPanel, BorderLayout.SOUTH);

		// Create some function buttons
		euButton = new JButton("EU Countries");
		euButton.setToolTipText("Selects all EU Countries");
		dataPanel.add(euButton, BorderLayout.WEST);
		euButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				list.removeSelectionInterval(0, list.getModel().getSize() - 1);
				for (int i = 0; i < listData.length; i++) {
					if (isEUMember(listData[i].getCountryCode())) {
						list.addSelectionInterval(i, i);
					}
				}
			}
		});

		noneuButton = new JButton("Non EU Countries");
		noneuButton.setToolTipText("World without EU Countries");
		dataPanel.add(noneuButton, BorderLayout.EAST);
		noneuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				list.removeSelectionInterval(0, list.getModel().getSize() - 1);
				for (int i = 0; i < listData.length; i++) {
					if (!isEUMember(listData[i].getCountryCode())) {
						list.addSelectionInterval(i, i);
					}
				}
			}
		});

		final JOptionPane pane = new JOptionPane(topPanel, // message
				JOptionPane.INFORMATION_MESSAGE, // messageType
				JOptionPane.OK_CANCEL_OPTION); // optionType
		final JDialog selectDialog = pane.createDialog(null, "Select countries");
		selectDialog.setVisible(true);
		Integer okCancel = (Integer) pane.getValue();
		if (okCancel != null && okCancel == JOptionPane.OK_OPTION) {
			log.debug("ok");
			Object[] obj = list.getSelectedValues();
			countries = new MCountry[obj.length];
			for (int i = 0; i < obj.length; i++) {
				countries[i] = (MCountry) obj[i];
			}
		} else {
			log.debug("cancel");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if (countries == null) {
			return "canceled by user";
		}
		final Properties ctx = Env.getCtx();
		MTax origTax = new MTax(ctx, taxId, get_TrxName());

		try {
			PreparedStatement pstmt = DB
					.prepareStatement(
							" INSERT INTO C_TAX_ACCT "
									+ " (C_TAX_ID,C_ACCTSCHEMA_ID,AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED, "
									+ " CREATEDBY,UPDATED,UPDATEDBY,T_DUE_ACCT,T_LIABILITY_ACCT,T_CREDIT_ACCT,T_RECEIVABLES_ACCT, "
									+ " T_EXPENSE_ACCT,T_Revenue_Acct,T_PayDiscount_Exp_Acct,T_PayDiscount_Rev_Acct)  "
									+ " SELECT ?, C_ACCTSCHEMA_ID, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE, now(), "
									+ " ?, now(), ?, T_DUE_ACCT,T_LIABILITY_ACCT,T_CREDIT_ACCT,T_RECEIVABLES_ACCT, "
									+ " T_EXPENSE_ACCT,T_Revenue_Acct,T_PayDiscount_Exp_Acct,T_PayDiscount_Rev_Acct "
									+ " FROM C_TAX_ACCT WHERE C_TAX_ID=?  ", get_TrxName());
			
			PreparedStatement pstmt_delete = DB
			.prepareStatement(
					" DELETE FROM C_TAX_ACCT WHERE C_TAX_ID=? AND AD_CLIENT_ID=? ", get_TrxName());

			for (int i = 0; i < countries.length; i++) {
				log.info("selected: " + countries[i].getName());

				MTax duplicateTax = new MTax(ctx, 0, get_TrxName());
				duplicateTax.setAD_Org_ID(origTax.getAD_Client_ID());
				//duplicateTax.setName(origTax.getRate() + "% " + countries[i].getName() + " - " + formatDate(origTax.getValidFrom(), DateFormat.MEDIUM));
				duplicateTax.setName(origTax.getName () + "-" + origTax.getRate() + "% " + countries[i].getName());
				duplicateTax.setDescription(origTax.getDescription());
				duplicateTax.setIsActive(origTax.isActive());
				duplicateTax.setIsDefault(false);
				duplicateTax.setC_TaxCategory_ID(origTax.getC_TaxCategory_ID());
				duplicateTax.setValidFrom(origTax.getValidFrom());
				duplicateTax.setIsTaxExempt(origTax.isTaxExempt());
				duplicateTax.setRequiresTaxCertificate(origTax.isRequiresTaxCertificate());
				duplicateTax.setIsSalesTax(origTax.isSalesTax());
				duplicateTax.setIsSummary(origTax.isSummary());
				duplicateTax.setParent_Tax_ID(origTax.getParent_Tax_ID());
				duplicateTax.setSOPOType(origTax.getSOPOType());
				duplicateTax.setTaxIndicator(origTax.getTaxIndicator());
				duplicateTax.setRate(origTax.getRate());
				
				//Changed
				//duplicateTax.setAD_Rule_ID(origTax.getAD_Rule_ID());

				duplicateTax.setC_Country_ID(origTax.getC_Country_ID());
				duplicateTax.setTo_Country_ID(countries[i].get_ID());
				if (!duplicateTax.save(get_TrxName())) {
					log.error("unable to save tax: " + duplicateTax);
				} else {
					log.debug("tax created: " + duplicateTax);
				}
				// duplicate accounting infos
				final int duplicateTaxId = duplicateTax.get_ID();
				
				//delete existing accounting of duplicated tab (created by tax creation)
				pstmt_delete.setInt(1, duplicateTaxId);
				pstmt_delete.setInt(2, Env.getAD_Client_ID(ctx));
				int deleted = pstmt_delete.executeUpdate();
				log.debug("deleted: " + deleted);
				//duplicate tax accounting
				pstmt.setInt(1, duplicateTaxId);
				pstmt.setInt(2, Env.getAD_User_ID(ctx));
				pstmt.setInt(3, Env.getAD_User_ID(ctx));
				pstmt.setInt(4, taxId);
				int result = pstmt.executeUpdate();
				if (result <=0) {
					log.error("unable to create tax accounting for tax: " + duplicateTax);
				}
			}
			
			DB.close(pstmt_delete);
			DB.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQLException: " + e.getLocalizedMessage());
			return "SQLException: " + e.getLocalizedMessage();
		}
		return "tax duplicated";
	}

	 /** contains iso code of all EU countries */
	static private Vector<String> euCountryCodes = new Vector<String>();
	
	static {
		// init EU countries
		euCountryCodes.add("BE");// belgium
		euCountryCodes.add("BG");// bulgaria
		euCountryCodes.add("DK");// denmark
		euCountryCodes.add("DE");// germany
		euCountryCodes.add("EE");// estonia
		euCountryCodes.add("FI");// finland
		euCountryCodes.add("FR");// france
		euCountryCodes.add("GR");// greece
		euCountryCodes.add("GB");// great britain
		euCountryCodes.add("IE");// ireland
		euCountryCodes.add("IT");// italy
		euCountryCodes.add("LV");// Latvia
		euCountryCodes.add("LT");// Lithuania
		euCountryCodes.add("LU");// Luxembourg
		euCountryCodes.add("MT");// Malta
		euCountryCodes.add("NL");// Netherlands
		euCountryCodes.add("AT");// Austria
		euCountryCodes.add("PL");// Poland
		euCountryCodes.add("PT");// Portugal
		euCountryCodes.add("RO");// Romania
		euCountryCodes.add("SE");// Sweden
		euCountryCodes.add("SI");// Slovenia
		euCountryCodes.add("SK");// Slovak Republic
		euCountryCodes.add("ES");// Spain
		euCountryCodes.add("CZ");// Czech Republic
		euCountryCodes.add("HU");// Hungary
		euCountryCodes.add("UK");// United Kingdom
		euCountryCodes.add("CY");// Cyprus
		// steuerrechtlich auch..
		euCountryCodes.add("FL");// Liechtenstein
	}
	
	static public boolean isEUMember(String countryCode){
		return euCountryCodes.contains(countryCode);
	}
}
