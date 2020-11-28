/**
 *
 */
package de.metas.contracts.flatrate.impexp;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.CreateFlatrateTermRequest;
import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.pricing.IPricingResult;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Properties;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author cg
 *
 */
/* package */ class FlatrateTermImporter
{
	public static FlatrateTermImporter newInstance(@NonNull final FlatrateTermImportProcess process)
	{
		return new FlatrateTermImporter(process);
	}

	// services
	private final transient IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private static final Logger logger = LogManager.getLogger(FlatrateTermImporter.class);

	final private FlatrateTermImportProcess process;


	private FlatrateTermImporter(FlatrateTermImportProcess process)
	{
		this.process = process;
	}

	private Properties getCtx()
	{
		return process.getCtx();
	}

	public I_C_Flatrate_Term importRecord(final I_I_Flatrate_Term importRecord)
	{
		final ProductId productId = ProductId.ofRepoId(importRecord.getM_Product_ID());
		final ProductAndCategoryId productAndCategoryId = Services.get(IProductDAO.class).retrieveProductAndCategoryIdByProductId(productId);

		final CreateFlatrateTermRequest createFlatrateTermRequest = CreateFlatrateTermRequest.builder()
			.context(PlainContextAware.newWithThreadInheritedTrx())
			.bPartner(importRecord.getC_BPartner())
			.conditions(importRecord.getC_Flatrate_Conditions())
			.startDate(importRecord.getStartDate())
			.productAndCategoryId(productAndCategoryId)
			.completeIt(false)
			.build();

		final I_C_Flatrate_Term contract = flatrateBL.createTerm(createFlatrateTermRequest);

		if (contract == null)
		{
			throw new AdempiereException("contract not created");
		}

		setBillUser(contract, getCtx());
		setDropShipBPartner(importRecord, contract);
		setDropShipUser(contract, getCtx());
		setDropShipLocation(contract, getCtx());
		contract.setM_Product_ID(productId.getRepoId());
		setUOM(contract, productId);
		contract.setPriceActual(importRecord.getPrice());
		setPlannedQtyPerUnit(importRecord, contract);
		setEndDate(importRecord, contract);
		setMasterStartdDate(importRecord, contract);
		setMasterEnddDate(importRecord, contract);
		setTaxCategoryAndIsTaxIncluded(contract);
		// important to ended if needed, before saving
		endContractIfNeeded(importRecord, contract);
		InterfaceWrapperHelper.save(contract);

		if (!isEndedContract(importRecord))
		{
			flatrateBL.complete(contract);
		}

		logger.trace("Insert FlaterateTerm - {}", contract);

		//
		// Link back the contract to current import record
		importRecord.setC_Flatrate_Term(contract);

		return contract;
	}

	private static void setBillUser(@NonNull final I_C_Flatrate_Term contract, final Properties ctx)
	{
		final int billPartnerId = contract.getBill_BPartner_ID();
		final I_AD_User billUser = FlatrateTermImportFinder.findBillUser(ctx, billPartnerId);
		if (billUser != null)
		{
			contract.setBill_User(billUser);
		}
	}

	private void setDropShipBPartner(@NonNull final I_I_Flatrate_Term importRecord, @NonNull final I_C_Flatrate_Term contract)
	{
		final int dropShipBPartnerId = importRecord.getDropShip_BPartner_ID() > 0 ? importRecord.getDropShip_BPartner_ID() : importRecord.getC_BPartner_ID();
		if (dropShipBPartnerId <= 0)
		{
			throw new AdempiereException("DropShip BPartner not found");
		}
		contract.setDropShip_BPartner_ID(dropShipBPartnerId);
	}

	private static void setDropShipUser(@NonNull final I_C_Flatrate_Term contract, final Properties ctx)
	{
		int dropShipBPartnerId = contract.getDropShip_BPartner_ID();
		final I_AD_User dropShipUser = FlatrateTermImportFinder.findDropShipUser(ctx, dropShipBPartnerId);
		if (dropShipUser != null)
		{
			contract.setDropShip_User(dropShipUser);
		}
	}

	private static void setDropShipLocation(@NonNull final I_C_Flatrate_Term contract, final Properties ctx)
	{
		int dropShipBPartnerId = contract.getDropShip_BPartner_ID();
		final I_C_BPartner_Location dropShipBPLocation = FlatrateTermImportFinder.findBPartnerShipToLocation(ctx, dropShipBPartnerId);
		if (dropShipBPLocation != null)
		{
			contract.setDropShip_Location(dropShipBPLocation);
		}
	}


	private void setUOM(@NonNull final I_C_Flatrate_Term contract, @NonNull final ProductId productId)
	{
		final UomId uomId = Services.get(IProductBL.class).getStockUOMId(productId);
		contract.setC_UOM_ID(uomId.getRepoId());
	}

	private void setPlannedQtyPerUnit(@NonNull final I_I_Flatrate_Term importRecord, @NonNull final I_C_Flatrate_Term contract)
	{
		if (importRecord.getQty() != null && importRecord.getQty().intValue() > 0)
		{
			contract.setPlannedQtyPerUnit(importRecord.getQty());
		}
	}

	private void setEndDate(@NonNull final I_I_Flatrate_Term importRecord, @NonNull final I_C_Flatrate_Term contract)
	{
		if (importRecord.getEndDate() != null)
		{
			contract.setEndDate(importRecord.getEndDate());
		}
	}

	private void setMasterStartdDate(@NonNull final I_I_Flatrate_Term importRecord, @NonNull final I_C_Flatrate_Term contract)
	{
		if (importRecord.getMasterStartDate() != null)
		{
			contract.setMasterStartDate(importRecord.getMasterStartDate());
		}
	}

	private void setMasterEnddDate(@NonNull final I_I_Flatrate_Term importRecord, @NonNull final I_C_Flatrate_Term contract)
	{
		if (importRecord.getMasterEndDate() != null)
		{
			contract.setMasterEndDate(importRecord.getMasterEndDate());
		}
	}

	private boolean isEndedContract(@NonNull final I_I_Flatrate_Term importRecord)
	{
		final Timestamp contractEndDate = importRecord.getEndDate();
		final Timestamp today = SystemTime.asDayTimestamp();
		return contractEndDate != null && today.after(contractEndDate);
	}

	private void endContractIfNeeded(@NonNull final I_I_Flatrate_Term importRecord, @NonNull final I_C_Flatrate_Term contract)
	{
		if (isEndedContract(importRecord))
		{
			contract.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
			contract.setNoticeDate(contract.getEndDate());
			contract.setIsAutoRenew(false);
			contract.setProcessed(true);
			contract.setDocAction(X_C_Flatrate_Term.DOCACTION_None);
			contract.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		}
	}

	private void setTaxCategoryAndIsTaxIncluded(@NonNull final I_C_Flatrate_Term newTerm)
	{
		final IPricingResult pricingResult = calculateFlatrateTermPrice(newTerm);
		newTerm.setC_TaxCategory_ID(TaxCategoryId.toRepoId(pricingResult.getTaxCategoryId()));
		newTerm.setIsTaxIncluded(pricingResult.isTaxIncluded());
	}

	private IPricingResult calculateFlatrateTermPrice(@NonNull final I_C_Flatrate_Term newTerm)
	{
		return FlatrateTermPricing.builder()
				.termRelatedProductId(ProductId.ofRepoId(newTerm.getM_Product_ID()))
				.qty(newTerm.getPlannedQtyPerUnit())
				.term(newTerm)
				.priceDate(TimeUtil.asLocalDate(newTerm.getStartDate()))
				.build()
				.computeOrThrowEx();
	}
}
