package de.metas.invoicecandidate.modelvalidator;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import de.metas.bpartner.service.impl.CalculateCreditStatusRequest;
import de.metas.bpartner.service.impl.CreditStatus;
import de.metas.currency.ICurrencyBL;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.exception.DocumentActionException;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.approvedforinvoice.ApprovedForInvoicingService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.order.event.OrderUserNotifications;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private static final AdMessageKey OPERATION_NOT_SUPPORTED_APPROVED_FOR_INVOICE = AdMessageKey.of("Operation_Not_Supported_Approved_For_Invoice");

	private final ApprovedForInvoicingService approvedForInvoicingService;

	public C_Order(@NonNull final ApprovedForInvoicingService approvedForInvoicingService)
	{
		this.approvedForInvoicingService = approvedForInvoicingService;
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REACTIVATE })
	public void invalidateInvoiceCandidates(final I_C_Order order)
	{
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(order);
	}

	private final static String SYS_CONFIG_UserStringElement1_DirectUpdateOrderToInvoiceCandidate = "UserStringElement1.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement2_DirectUpdateOrderToInvoiceCandidate = "UserStringElement2.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement3_DirectUpdateOrderToInvoiceCandidate = "UserStringElement3.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement4_DirectUpdateOrderToInvoiceCandidate = "UserStringElement4.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement5_DirectUpdateOrderToInvoiceCandidate = "UserStringElement5.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement6_DirectUpdateOrderToInvoiceCandidate = "UserStringElement6.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement7_DirectUpdateOrderToInvoiceCandidate = "UserStringElement7.DirectUpdateOrderToInvoiceCandidate";
	private final static String SYS_CONFIG_UserStringElement1_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement1.DirectUpdateOrderToInvoice.IfReversed";
	private final static String SYS_CONFIG_UserStringElement2_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement2.DirectUpdateOrderToInvoice.IfReversed";
	private final static String SYS_CONFIG_UserStringElement3_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement3.DirectUpdateOrderToInvoice.IfReversed";
	private final static String SYS_CONFIG_UserStringElement4_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement4.DirectUpdateOrderToInvoice.IfReversed";
	private final static String SYS_CONFIG_UserStringElement5_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement5.DirectUpdateOrderToInvoice.IfReversed";
	private final static String SYS_CONFIG_UserStringElement6_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement6.DirectUpdateOrderToInvoice.IfReversed";
	private final static String SYS_CONFIG_UserStringElement7_DirectUpdateOrderToInvoice_IfReversed = "UserStringElement7.DirectUpdateOrderToInvoice.IfReversed";
	final BPartnerStatsService bPartnerStatsService = SpringContextHolder.instance.getBean(BPartnerStatsService.class);
	final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
	final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void checkCreditLimit(@NonNull final I_C_Order order)
	{
		if (!isCheckCreditLimitNeeded(order))
		{
			return;
		}

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(order.getBill_BPartner_ID());
		final BigDecimal soCreditUsed = stats.getSoCreditUsed();
		final CreditStatus soCreditStatus = stats.getSoCreditStatus();
		final Timestamp dateOrdered = order.getDateOrdered();

		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(order.getBill_BPartner_ID(), dateOrdered);

		if (CreditStatus.CreditStop.equals(soCreditStatus))
		{
			final ITranslatableString errorMessage = TranslatableStrings.builder()
					.appendADElement("BPartnerCreditStop").append(":")
					.append(" ").appendADElement("SO_CreditUsed").append("=").append(soCreditUsed, DisplayType.Amount)
					.append(", ").appendADElement("SO_CreditLimit").append("=").append(creditLimit, DisplayType.Amount)
					.build();

			notifyUserOrThrowException(order, errorMessage);

			return;

		}

		if (CreditStatus.CreditHold.equals(soCreditStatus))
		{
			final ITranslatableString errorMessage = TranslatableStrings.builder()
					.appendADElement("BPartnerCreditHold").append(":")
					.append(" ").appendADElement("SO_CreditUsed").append("=").append(soCreditUsed, DisplayType.Amount)
					.append(", ").appendADElement("SO_CreditLimit").append("=").append(creditLimit, DisplayType.Amount)
					.build();

			notifyUserOrThrowException(order, errorMessage);

			return;
		}

		final BigDecimal grandTotal = currencyBL.convertBase(
				order.getGrandTotal(),
				CurrencyId.ofRepoId(order.getC_Currency_ID()),
				order.getDateOrdered().toInstant(),
				CurrencyConversionTypeId.ofRepoIdOrNull(order.getC_ConversionType_ID()),
				ClientId.ofRepoId(order.getAD_Client_ID()),
				OrgId.ofRepoId(order.getAD_Org_ID()));

		final CalculateCreditStatusRequest request = CalculateCreditStatusRequest.builder()
				.stat(stats)
				.additionalAmt(grandTotal) // null is threated like zero
				.date(dateOrdered)
				.build();
		final CreditStatus calculatedSOCreditStatus = bPartnerStatsService.calculateProjectedSOCreditStatus(request);

		if (CreditStatus.CreditHold.equals(calculatedSOCreditStatus))
		{
			final ITranslatableString errorMessage = TranslatableStrings.builder()
					.appendADElement("BPartnerOverOCreditHold").append(":")
					.append(" ").appendADElement("SO_CreditUsed").append("=").append(soCreditUsed, DisplayType.Amount)
					.append(", ").appendADElement("GrandTotal").append("=").append(grandTotal, DisplayType.Amount)
					.append(", ").appendADElement("SO_CreditLimit").append("=").append(creditLimit, DisplayType.Amount)
					.build();
			notifyUserOrThrowException(order, errorMessage);
		}
	}

	private void notifyUserOrThrowException(final @NonNull I_C_Order order, final @NonNull ITranslatableString errorMessage)
	{
		final boolean isEnforceSOCreditStatus = bPartnerStatsService.isEnforceCreditStatus(ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID()));
		if (!isEnforceSOCreditStatus)
		{
			final OrderUserNotifications orderUserNotifications = OrderUserNotifications.newInstance();
			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			orderUserNotifications.notifyAboutCreditLimit(order, errorMessage.translate(adLanguage));
		}
		else
		{
			throw new AdempiereException(errorMessage);
		}
	}

	private boolean isCheckCreditLimitNeeded(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return false;
		}

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(order.getBill_BPartner_ID());
		if (CreditStatus.NoCreditCheck.equals(stats.getSoCreditStatus()))
		{
			return false;
		}

		final I_C_DocType dt = docTypeDAO.getRecordById(order.getC_DocTypeTarget_ID());

		final PaymentRule paymentRule = PaymentRule.ofCode(order.getPaymentRule());
		if (X_C_DocType.DOCSUBTYPE_POSOrder.equals(dt.getDocSubType())
				&& paymentRule.isCash()
				&& !sysConfigBL.getBooleanValue("CHECK_CREDIT_ON_CASH_POS_ORDER", true, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			// ignore -- don't validate for Cash POS Orders depending on sysconfig parameter
			return false;
		}
		else if (X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
				&& !sysConfigBL.getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			return false;
		}

		return true;
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL })
	public void checkAnyAssociatedInvoiceCandidateClearedForInvoice(@NonNull final I_C_Order order) throws OperationNotSupportedException
	{
		final TableRecordReference recordReference = TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID());

		if (approvedForInvoicingService.areAnyCandidatesApprovedForInvoice(recordReference))
		{
			throw new DocumentActionException(OPERATION_NOT_SUPPORTED_APPROVED_FOR_INVOICE);
		}
	}
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_Order.COLUMNNAME_UserElementString1,
					I_C_Order.COLUMNNAME_UserElementString2,
					I_C_Order.COLUMNNAME_UserElementString3,
					I_C_Order.COLUMNNAME_UserElementString4,
					I_C_Order.COLUMNNAME_UserElementString5,
					I_C_Order.COLUMNNAME_UserElementString6,
					I_C_Order.COLUMNNAME_UserElementString7 }

	)
	public void updateInvoiceCandidatesUserElementStrings(@NonNull final I_C_Order order)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID());

		final boolean use1_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement1_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);
		final boolean use2_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement2_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);
		final boolean use3_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement3_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);
		final boolean use4_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement4_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);
		final boolean use5_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement5_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);
		final boolean use6_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement6_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);
		final boolean use7_orderToIc = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement7_DirectUpdateOrderToInvoiceCandidate, false, clientAndOrgId);

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForOrderId(OrderId.ofRepoId(order.getC_Order_ID()));
		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			if (use1_orderToIc)
			{
				invoiceCandidate.setUserElementString1(order.getUserElementString1());
			}
			if (use2_orderToIc)
			{
				invoiceCandidate.setUserElementString2(order.getUserElementString2());
			}
			if (use3_orderToIc)
			{
				invoiceCandidate.setUserElementString3(order.getUserElementString3());
			}
			if (use4_orderToIc)
			{
				invoiceCandidate.setUserElementString4(order.getUserElementString4());
			}
			if (use5_orderToIc)
			{
				invoiceCandidate.setUserElementString5(order.getUserElementString5());
			}
			if (use6_orderToIc)
			{
				invoiceCandidate.setUserElementString6(order.getUserElementString6());
			}
			if (use7_orderToIc)
			{
				invoiceCandidate.setUserElementString7(order.getUserElementString7());
			}

			invoiceCandDAO.save(invoiceCandidate);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_C_Order.COLUMNNAME_UserElementString1,
					I_C_Order.COLUMNNAME_UserElementString2,
					I_C_Order.COLUMNNAME_UserElementString3,
					I_C_Order.COLUMNNAME_UserElementString4,
					I_C_Order.COLUMNNAME_UserElementString5,
					I_C_Order.COLUMNNAME_UserElementString6,
					I_C_Order.COLUMNNAME_UserElementString7 }

	)
	public void updateReversedInvoicesUserElementStrings(@NonNull final I_C_Order order)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID());

		final boolean use1_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement1_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);
		final boolean use2_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement2_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);
		final boolean use3_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement3_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);
		final boolean use4_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement4_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);
		final boolean use5_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement5_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);
		final boolean use6_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement6_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);
		final boolean use7_orderToReversedInvoice = sysConfigBL.getBooleanValue(SYS_CONFIG_UserStringElement7_DirectUpdateOrderToInvoice_IfReversed, false, clientAndOrgId);

		final List<I_C_Invoice> invoices = invoiceDAO.getInvoicesForOrderIds(Collections.singletonList(OrderId.ofRepoId(order.getC_Order_ID())));

		for (final I_C_Invoice invoice : invoices)
		{
			final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(invoice.getDocStatus());
			if (!docStatus.isReversed())
			{
				continue;
			}

			if (use1_orderToReversedInvoice)
			{
				invoice.setUserElementString1(order.getUserElementString1());
			}
			if (use2_orderToReversedInvoice)
			{
				invoice.setUserElementString2(order.getUserElementString2());
			}
			if (use3_orderToReversedInvoice)
			{
				invoice.setUserElementString3(order.getUserElementString3());
			}
			if (use4_orderToReversedInvoice)
			{
				invoice.setUserElementString4(order.getUserElementString4());
			}
			if (use5_orderToReversedInvoice)
			{
				invoice.setUserElementString5(order.getUserElementString5());
			}
			if (use6_orderToReversedInvoice)
			{
				invoice.setUserElementString6(order.getUserElementString6());
			}
			if (use7_orderToReversedInvoice)
			{
				invoice.setUserElementString7(order.getUserElementString7());
			}

			invoiceDAO.save(invoice);
		}
	}

}
