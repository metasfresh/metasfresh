package de.metas.contracts.pricing;

import ch.qos.logback.classic.Level;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.modular.ModularContractPriceRepository;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.i18n.BooleanWithReason;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.IPricingRule;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

/**
 * This pricing rule applies if the given {@link IPricingContext}'s referenced object is a {@link I_C_Invoice_Candidate}.
 * <p>
 * If that is given and if the
 */
public class ContractSpecificPricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(ContractSpecificPricingRule.class);

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final ModularContractPriceRepository modularContractPriceRepository = SpringContextHolder.instance.getBean(ModularContractPriceRepository.class);

	@Override
	public boolean applies(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		if (result.isCalculated())
		{
			loggable.addLog("Not applying because already calculated");
			return false;
		}

		if (pricingCtx.getCountryId() == null)
		{
			loggable.addLog("Not applying because pricingCtx has no C_Country_ID; pricingCtx={}", pricingCtx);
			return false;
		}

		final Object referencedObject = pricingCtx.getReferencedObject();

		if (!(referencedObject instanceof final I_C_Invoice_Candidate invoiceCandidate))
		{
			loggable.addLog("Not applying because referencedObject is not an C_Invoice_Candidate; referencedObject={}", referencedObject);
			return false;
		}

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoIdOrNull(invoiceCandidate.getC_Flatrate_Term_ID());

		if (flatrateTermId == null)
		{
			loggable.addLog("Not applying because invoice candidate does not contain a flatrate term.");
			return false;
		}

		final ModularContractModuleId modularContractModuleId = ModularContractModuleId.ofRepoIdOrNull(invoiceCandidate.getModCntr_Module_ID());

		if (modularContractModuleId == null)
		{
			loggable.addLog("Not applying because invoice candidate does not contain a modular contract module ID.");
			return false;
		}

		final String typeConditions = flatrateDAO.getById(flatrateTermId).getType_Conditions();

		if (!TypeConditions.INTERIM_INVOICE.getCode().equals(typeConditions) && !TypeConditions.MODULAR_CONTRACT.getCode().equals(typeConditions))
		{
			loggable.addLog("Not applying because referenced C_Flatrate_Term.Type_Conditions={} (should be: either {} or {})", typeConditions, TypeConditions.INTERIM_INVOICE.getCode(), TypeConditions.MODULAR_CONTRACT.getCode());
			return false;
		}

		return true;
	}

	@Override
	public void calculate(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final Object referencedObject = pricingCtx.getReferencedObject();
		final I_C_Invoice_Candidate invoiceCandidate = (I_C_Invoice_Candidate)referencedObject;
		final ModularContractModuleId modularContractModuleId = ModularContractModuleId.ofRepoId(invoiceCandidate.getModCntr_Module_ID());
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(invoiceCandidate.getC_Flatrate_Term_ID());
		final I_ModCntr_Specific_Price contractSpecificPrice = modularContractPriceRepository.retrievePriceForProductAndContract(modularContractModuleId, flatrateTermId);
		if (contractSpecificPrice == null)
		{
			return;
		}
		loggable.addLog("Specific price found for ModularContractModuleId={}, FlatrateTermId={} =>  ", modularContractModuleId, flatrateTermId, contractSpecificPrice.getPrice());

		result.setPriceStd(contractSpecificPrice.getPrice());
		result.setCurrencyId(CurrencyId.ofRepoId(contractSpecificPrice.getC_Currency_ID()));
		result.setPriceEditable(true);
		result.setDiscountEditable(false);
		result.setEnforcePriceLimit(BooleanWithReason.FALSE);
		result.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.CatchWeight);
		result.setTaxIncluded(false);
		result.setPricingSystemId(PricingSystemId.ofRepoId(invoiceCandidate.getM_PricingSystem_ID()));
		result.setPriceListVersionId(PriceListVersionId.ofRepoId(invoiceCandidate.getM_PriceList_Version_ID()));
		result.setTaxCategoryId(TaxCategoryId.ofRepoId(contractSpecificPrice.getC_TaxCategory_ID()));
		result.setCalculated(true);
		result.setPriceUomId(UomId.ofRepoId(contractSpecificPrice.getC_UOM_ID()));
	}

}
