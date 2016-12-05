package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MRelation;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.MHRProcess;
import org.slf4j.Logger;

import de.metas.commission.exception.CommissionException;
import de.metas.commission.inout.model.validator.M_InOut;
import de.metas.commission.interfaces.I_AD_User;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_BPartner_Location;
import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.invoice.model.validator.C_Invoice;
import de.metas.commission.invoice.model.validator.C_InvoiceLine;
import de.metas.commission.invoice.spi.impl.CommissionInvoiceCopyHandler;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_HR_Movement;
import de.metas.commission.order.spi.impl.CommissionOrderCopyHandler;
import de.metas.commission.pricing.spi.impl.CommissionPlvCreationListener;
import de.metas.commission.service.IComRelevantPoBL;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionPayrollBL;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.IHierarchyBL;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.IOrderLineBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorCondition;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.service.impl.ComRelevantPoBL;
import de.metas.commission.service.impl.CommissionFactBL;
import de.metas.commission.service.impl.CommissionPayrollBL;
import de.metas.commission.service.impl.ContractBL;
import de.metas.commission.service.impl.FieldAccessBL;
import de.metas.commission.service.impl.HierarchyBL;
import de.metas.commission.service.impl.InstanceTriggerBL;
import de.metas.commission.service.impl.OrderLineBL;
import de.metas.commission.service.impl.SalesRepFactBL;
import de.metas.commission.service.impl.SponsorBL;
import de.metas.commission.service.impl.SponsorCondition;
import de.metas.commission.util.CommissionConstants;
import de.metas.commission.util.Messages;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocumentPA;
import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

/**
 * Commission Module Main Validator
 * 
 * @author ts
 * @author Cris
 * 
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_(2009_0023_G107)'>(2009 0023 G107)</a>"
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_(2009_0023_G108)'>(2009 0023 G108)</a>"
 */
public class CommissionValidator implements ModelValidator
{
	public static final String CTX_Commission_Calendar_ID = "#Commission_Calendar_ID";

	public static final String CTX_Commission_System_ID = "#Commission_System_ID";

	public static final String SYSCONFIG_INVOICE_C_Charge_ID = "de.metas.commission.CommissionInvoice.C_Charge_ID";

	private static final Logger logger = LogManager.getLogger(CommissionValidator.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		// client = null for global validator
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
			logger.info("Initializing validator: " + this + " for client " + client.toString());
		}
		else
		{
			logger.info("Initializing global validator: " + this);
		}

		engine.addModelValidator(new C_Sponsor_SalesRep(), client);
		engine.addModelValidator(new C_AdvCommissionCondition(), client);
		engine.addModelValidator(new C_AdvCommissionTerm(), client);
		engine.addModelValidator(new AD_User_Roles(), client);

		engine.addDocValidate(I_HR_Process.Table_Name, this);
		engine.addDocValidate(org.compiere.model.I_C_Invoice.Table_Name, this);

		//
		// initializing commission services
		Services.registerService(ISponsorBL.class, new SponsorBL());

		Services.registerService(ICommissionFactBL.class, new CommissionFactBL());
		Services.registerService(ICommissionPayrollBL.class, new CommissionPayrollBL());
		Services.registerService(IFieldAccessBL.class, new FieldAccessBL());

		Services.registerService(IInstanceTriggerBL.class, new InstanceTriggerBL());
		Services.registerService(IHierarchyBL.class, new HierarchyBL());
		Services.registerService(IOrderLineBL.class, new OrderLineBL());
		Services.registerService(ISalesRepFactBL.class, new SalesRepFactBL());
		Services.registerService(IComRelevantPoBL.class, new ComRelevantPoBL());

		Services.registerService(IContractBL.class, new ContractBL());

		Services.registerService(ISponsorCondition.class, new SponsorCondition());

		engine.addModelValidator(new ComDocValidator(), client);
		engine.addModelValidator(new SponsorCondValidator(), client);
		engine.addModelValidator(new SponsorCondLineValidator(), client);
		engine.addModelValidator(new SponsorSalesRepValidator(), client);
		engine.addModelValidator(new C_BPartner_Location(), client);
		engine.addModelValidator(new C_Invoice_Line_Alloc(), client);

		engine.addModelValidator(new CommissionRelevantPOs(), client); // task 05035

		// task 07286: register MVs, a PLV creation listener and copy handlers, all to replace the former jboss-aop-aspects
		{
			engine.addModelValidator(new C_InvoiceLine(), client);
			engine.addModelValidator(new C_Invoice(), client);
			engine.addModelValidator(new M_InOut(), client);
			
			Services.get(IPriceListBL.class).addPlvCreationListener(new CommissionPlvCreationListener());

			Services.get(ICopyHandlerBL.class).registerCopyHandler(
					org.compiere.model.I_C_Order.class, 
					new IQueryFilter<Pair<org.compiere.model.I_C_Order, org.compiere.model.I_C_Order>>()
					{
						@Override
						public boolean accept(Pair<org.compiere.model.I_C_Order, org.compiere.model.I_C_Order> model)
						{
							return true;
						}
					},
					new CommissionOrderCopyHandler());
			
			Services.get(ICopyHandlerBL.class).registerCopyHandler(
					org.compiere.model.I_C_Invoice.class, 
					new IQueryFilter<Pair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice>>()
					{
						@Override
						public boolean accept(Pair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice> model)
						{
							return true;
						}
					},
					new CommissionInvoiceCopyHandler());
		}

	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	public void afterLoadPreferences(final Properties ctx)
	{
		setCommission_Calendar_ID(ctx);
	}

	private void setCommission_Calendar_ID(final Properties ctx)
	{
		final I_C_BPartner bp = InterfaceWrapperHelper.create(MUser.get(ctx).getC_BPartner(), I_C_BPartner.class);
		if (bp == null || bp.getC_BPartner_ID() == 0)
		{
			return;
		}

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final Timestamp date = Env.getContextAsDate(ctx, "#Date");
		final I_C_Sponsor sponsor = retrieveSalesrepOrCustomerSponsorAt(bp, date);

		final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, date, null);
		if (contract == null)
		{
			return;
		}
		final int C_Calendar_ID = contract.getC_Calendar_ID();
		if (C_Calendar_ID >= 0)
		{
			Env.setContext(ctx, CommissionValidator.CTX_Commission_Calendar_ID, C_Calendar_ID);
			Env.setContext(ctx, CommissionValidator.CTX_Commission_System_ID, contract.getC_AdvComSystem_ID());
		}
	}

	private I_C_Sponsor retrieveSalesrepOrCustomerSponsorAt(final I_C_BPartner bp, final Timestamp date)
	{
		final List<I_C_Sponsor> sponsors = Services.get(ISponsorDAO.class).retrieveForSalesRepAt(bp, date);
		if (sponsors.size() > 1)
		{
			throw CommissionException.inconsistentConfig("More then one sponsor found for " + bp + ", date=" + date, bp);
		}

		final I_C_Sponsor sponsor;

		if (sponsors.isEmpty())
		{
			sponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(bp, true);
			CommissionValidator.logger.info("No sales rep sponsor found for " + bp + " at date=" + date + ". Using customer sponsor.");
		}
		else
		{
			sponsor = sponsors.get(0);
		}
		return sponsor;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		if (po instanceof MHRProcess)
		{
			return payrollValidate((MHRProcess)po, timing);
		}
		else if (po instanceof MInvoice)
		{
			return invoiceValidate((MInvoice)po, timing);
		}
		return null;
	}

	private String invoiceValidate(final MInvoice invoice, final int timing)
	{
		if (timing == ModelValidator.TIMING_AFTER_VOID || timing == ModelValidator.TIMING_AFTER_REVERSECORRECT)
		{
			for (final MInvoiceLine il : invoice.getLines())
			{
				MRelation.deleteForPO(il);
			}
		}
		return null;
	}

	private String payrollValidate(final MHRProcess po, final int timing)
	{
		if (timing == ModelValidator.TIMING_AFTER_COMPLETE)
		{
			return generateInvoices(po);
		}

		return null;
	}

	private String generateInvoices(final MHRProcess process)
	{
		final Map<Integer, Map<Integer, MInvoice>> bPartnerId2Inv = new HashMap<Integer, Map<Integer, MInvoice>>();

		for (final I_HR_Movement movement : getMovements(process))
		{
			final int bPartnerId = movement.getC_BPartner_ID();
			final int currencyId = movement.getC_Currency_ID();

			final MBPartner bPartner = MBPartner.get(process.getCtx(), bPartnerId);

			Map<Integer, MInvoice> currencyId2Invoice = bPartnerId2Inv.get(bPartnerId);

			if (currencyId2Invoice == null)
			{
				currencyId2Invoice = new HashMap<Integer, MInvoice>();
				bPartnerId2Inv.put(bPartnerId, currencyId2Invoice);
			}

			final MInvoice invoice = createCacheInvoice(process, bPartnerId, currencyId, bPartner, currencyId2Invoice);

			final MInvoiceLine ilPO = new MInvoiceLine(invoice);

			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(ilPO, I_C_InvoiceLine.class);
			il.setC_Tax_ID(movement.getC_Tax_ID());
			il.setQtyInvoiced(BigDecimal.ONE);
			il.setQtyEntered(BigDecimal.ONE);
			il.setPriceEntered(movement.getAmount());
			il.setPriceActual(movement.getAmount());
			// il.setPrice_UOM_ID(0); // 07090: this is one of the cases where we don't have a price UOM..maybe one day we should add a none/1-UOM for such cases.
			il.setDescription(movement.getDescription());
			il.setHR_Movement_ID(movement.getHR_Movement_ID());

			// 05129: tax category setting
			final Properties ctx = InterfaceWrapperHelper.getCtx(movement);
			final String trxName = InterfaceWrapperHelper.getTrxName(movement);

			final I_C_Tax tax = InterfaceWrapperHelper.create(ctx, movement.getC_Tax_ID(), I_C_Tax.class, trxName);

			il.setC_TaxCategory(tax.getC_TaxCategory());

			// 02527: Create an Attribute Set Instance.
			final I_C_Invoice icInvoice = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
			if (icInvoice.getM_AttributeSetInstance_ID() <= 0)
			{
				setInvoiceAttributeSet(process, bPartner, invoice, movement);
			}
			// 02527 end

			// 02416
			final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
			final int chargeId =
					sysConfigBL.getIntValue(CommissionValidator.SYSCONFIG_INVOICE_C_Charge_ID, 0, process.getAD_Client_ID(), process.getAD_Org_ID());
			Check.assume(chargeId != 0, "Missing AD_SysConfig record for " + CommissionValidator.SYSCONFIG_INVOICE_C_Charge_ID);

			il.setC_Charge_ID(chargeId);

			ilPO.saveEx();
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		for (final Map<Integer, MInvoice> currencyId2Invoice : bPartnerId2Inv.values())
		{
			for (final MInvoice invoice : currencyId2Invoice.values())
			{
				if (docActionBL.processIt(invoice, DocAction.ACTION_Complete))
				{
					invoice.saveEx();
				}
				else
				{
					final String msg = Messages.INVOICE_COMPLETE_IT_FAILURE_2P;
					final Object[] args = {
							invoice.getDocumentNo(),
							MetasfreshLastError.retrieveError().getValue() };

					return Services.get(IMsgBL.class).getMsg(process.getCtx(), msg, args);
				}

				final ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

				for (final MInvoiceLine il : invoice.getLines())
				{
					commissionFactBL.recordEmployeeIl(il, invoice, BigDecimal.ONE, IProcessingService.NO_AD_PINSTANCE_ID);
				}
			}
		}
		return null;
	}

	private void setInvoiceAttributeSet(final MHRProcess process, final MBPartner bPartner, final MInvoice invoice, final I_HR_Movement movement)
	{
		// get SponsorBL
		final I_C_BPartner icBPartner = InterfaceWrapperHelper.create(bPartner, I_C_BPartner.class);
		final I_C_Sponsor sponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(process.getCtx(), icBPartner, false, process.get_TrxName());
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		// get Period
		final I_C_Period calcPeriod = getPayrollPeriod(process.getCtx(), movement.getHR_Movement_ID(), process.get_TrxName());

		final I_C_Invoice icInvoice = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
		final I_M_AttributeSetInstance setInstance = sponsorBL.createSalaryGroupAttributeSet(sponsor, calcPeriod, invoice.getDateInvoiced());
		icInvoice.setM_AttributeSetInstance_ID(setInstance.getM_AttributeSetInstance_ID());
		invoice.saveEx();
	}

	private MInvoice createCacheInvoice(final MHRProcess process,
			final int bPartnerId, final int currencyId,
			final MBPartner bPartnerPO,
			final Map<Integer, MInvoice> currencyId2Invoice)
	{
		MInvoice invoicePO = currencyId2Invoice.get(currencyId);

		if (invoicePO == null)
		{
			final Properties ctx = process.getCtx();
			final String trxName = process.get_TrxName();

			invoicePO = new MInvoice(ctx, 0, trxName);
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(invoicePO, I_C_Invoice.class);

			invoice.setIsSOTrx(false);

			final IDocumentPA docPA = Services.get(IDocumentPA.class);
			final I_C_DocType docType = docPA.retrieve(ctx, process.getAD_Org_ID(), Constants.DOCBASETYPE_AEInvoice, CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CALC, true, trxName);

			invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
			invoice.setC_Currency_ID(currencyId);
			invoice.setDocAction(DocAction.ACTION_Complete);

			// TODO: introduce/use Services.get(IInvoiceBL.class).setBPartner(invoice, bPartnerPO);
			invoicePO.setBPartner(bPartnerPO);

			invoice.setDateInvoiced(SystemTime.asTimestamp());
			invoice.setDateAcct(process.getDateAcct());

			final de.metas.commission.service.IBPartnerDAO commissionBPartnerDAO = Services.get(de.metas.commission.service.IBPartnerDAO.class);

			final I_C_BPartner bPartner = InterfaceWrapperHelper.create(bPartnerPO, I_C_BPartner.class);
			final I_C_BPartner_Location bpLoc = commissionBPartnerDAO.retrieveCommissionToLocation(ctx, bPartnerId, trxName);
			final I_AD_User user = commissionBPartnerDAO.retrieveCommissionContact(ctx, bPartnerPO.get_ID(), trxName);

			invoice.setC_BPartner_Location_ID(bpLoc.getC_BPartner_Location_ID());
			if (user != null)
			{
				invoice.setAD_User_ID(user.getAD_User_ID());
			}

			final String addressField = Services.get(IBPartnerBL.class).mkFullAddress(bPartner, bpLoc, user, trxName);
			invoice.setBPartnerAddress(addressField);

			// TODO: get the payment rule from commission contract
			// FRESH-488: Set the payment rule 
			final String paymentRuleToUse = Services.get(IInvoiceBL.class).getDefaultPaymentRule();
			invoice.setPaymentRule(paymentRuleToUse);

			InterfaceWrapperHelper.save(invoice);

			currencyId2Invoice.put(currencyId, invoicePO);
		}

		return invoicePO;
	}

	private I_C_Period getPayrollPeriod(final Properties ctx, final int movementId, final String trxName)
	{
		int periodId = 0;

		final String sql =
				"SELECT prov_ber.c_period_id " +
						"FROM 	hr_allocationline al " +
						" 		JOIN c_advcommissionpayrollline prov_line ON al.record_id = prov_line.c_advcommissionpayrollline_id " +
						"		JOIN c_advcommissionpayroll prov_ber ON prov_line.c_advcommissionpayroll_id = prov_ber.c_advcommissionpayroll_id " +
						"WHERE  al.hr_movement_id = ? " +
						"GROUP BY al.hr_movement_id, prov_ber.c_advcommissionpayroll_id, prov_ber.c_period_id ";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, movementId);

			rs = pstmt.executeQuery();
			if (rs.next() && rs.isFirst() && rs.isLast())
			{
				periodId = rs.getInt(1);
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return InterfaceWrapperHelper.create(ctx, periodId, I_C_Period.class, trxName);
	}

	private Collection<I_HR_Movement> getMovements(final MHRProcess process)
	{

		final String whereClause = org.eevolution.model.I_HR_Movement.COLUMNNAME_HR_Process_ID + "=?";

		final Object[] parameters = { process.getHR_Process_ID() };

		final List<I_HR_Movement> result = new Query(process.getCtx(), I_HR_Movement.Table_Name, whereClause, process.get_TrxName())
				.setParameters(parameters)
				.setOnlyActiveRecords(true)
				.list(I_HR_Movement.class);
		return result;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		// nothing to do
		return null;
	}

	@Override
	public String toString()
	{
		return "CommissionValidator";
	}
}
