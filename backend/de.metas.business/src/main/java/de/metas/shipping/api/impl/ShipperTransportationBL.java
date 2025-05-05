package de.metas.shipping.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Package;
import org.compiere.model.X_C_DocType;

import java.math.BigDecimal;
import java.util.Iterator;

public class ShipperTransportationBL implements IShipperTransportationBL
{
	final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

	final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	final ITaxBL taxBL = Services.get(ITaxBL.class);

	@Override
	public I_M_ShippingPackage createShippingPackage(final I_M_ShipperTransportation shipperTransportation, final I_M_Package mpackage)
	{
		final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class, shipperTransportation);
		shippingPackage.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());

		updateShippingPackageFromPackage(shippingPackage, mpackage);
		InterfaceWrapperHelper.save(shippingPackage);

		return shippingPackage;
	}

	private void updateShippingPackageFromPackage(final I_M_ShippingPackage shippingPackage, final I_M_Package mpackage)
	{
		shippingPackage.setM_Package_ID(mpackage.getM_Package_ID());
		shippingPackage.setC_BPartner_ID(mpackage.getC_BPartner_ID());
		shippingPackage.setC_BPartner_Location_ID(mpackage.getC_BPartner_Location_ID());

		if (mpackage.getM_InOut_ID() > 0)
		{
			shippingPackage.setM_InOut_ID(mpackage.getM_InOut_ID());
			shippingPackage.setC_BPartner_ID(mpackage.getM_InOut().getC_BPartner_ID());
			shippingPackage.setC_BPartner_Location_ID(mpackage.getM_InOut().getC_BPartner_Location_ID());
		}

		// @TODO: Calculate PackageNetTotal and PackageWeight ??
		shippingPackage.setPackageNetTotal(mpackage.getPackageNetTotal());
		shippingPackage.setPackageWeight(mpackage.getPackageWeight());
	}

	@Override
	public void setC_DocType(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final int adClientId = shipperTransportation.getAD_Client_ID();
		final int adOrgId = shipperTransportation.getAD_Org_ID();

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(DocBaseType.ShipperTransportation)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build();
		final int docTypeId = docTypeDAO.getDocTypeId(query).getRepoId();

		shipperTransportation.setC_DocType_ID(docTypeId);
	}

	@Override
	public boolean isDeliveryInstruction(@NonNull final DocTypeId docTypeId)
	{
		final DocBaseAndSubType docBaseAndSubTypeById = docTypeDAO.getDocBaseAndSubTypeById(docTypeId);

		final DocBaseType docBaseType = docBaseAndSubTypeById.getDocBaseType();
		final String docSubType = docBaseAndSubTypeById.getDocSubType();

		if (!DocBaseType.ShipperTransportation.equals(docBaseType))
		{
			// this is not a transportation order doc type
			return false;
		}

		if (!X_C_DocType.DOCSUBTYPE_DeliveryInstruction.equals(docSubType))
		{
			return false;
		}

		return true;
	}

	@Override
	public Money getCreditUsedByOutgoingDeliveryInstructionsInCurrency(final BPartnerId bpartnerId, final CurrencyId currencyId)
	{

		final Iterator<I_M_ShippingPackage> shippingPackages = shipperTransportationDAO.retrieveCompletedOutgoingDeliveryInstructionLines(bpartnerId);

		Money creditUsedInDeliveryInstructions = Money.zero(currencyId);

		while (shippingPackages.hasNext())
		{
			final I_M_ShippingPackage shippingPackage = shippingPackages.next();

			final Money creditUsedPerShippingPackageInBaseCurrency = computeCreditUsedPerShippingPackageInCurrency(shippingPackage, currencyId);

			creditUsedInDeliveryInstructions = creditUsedInDeliveryInstructions.add(creditUsedPerShippingPackageInBaseCurrency);
		}

		return creditUsedInDeliveryInstructions;
	}

	private Money computeCreditUsedPerShippingPackageInCurrency(@NonNull final I_M_ShippingPackage shippingPackage, @NonNull final CurrencyId currencyId)
	{
		final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

		if (shippingPackage.getC_OrderLine_ID() <= 0)
		{
			return Money.zero(currencyId);
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(shippingPackage.getC_OrderLine_ID());

		final Quantity actualLoadQty = Quantitys.of(shippingPackage.getActualLoadQty(), UomId.ofRepoId(shippingPackage.getC_UOM_ID()));

		final CurrencyId orderLineCurrencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
		final Money qtyNetPriceFromOrderLine = Money.of(orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, actualLoadQty),
														orderLineCurrencyId);

		final TaxId taxId = TaxId.ofRepoId(orderLine.getC_Tax_ID());

		final boolean isTaxIncluded = orderLineBL.isTaxIncluded(orderLine);

		final CurrencyPrecision taxPrecision = orderLineBL.getTaxPrecision(orderLine);

		final Tax tax = taxBL.getTaxById(taxId);

		final BigDecimal taxAmt = tax.calculateTax(qtyNetPriceFromOrderLine.toBigDecimal(), isTaxIncluded, taxPrecision.toInt()).getTaxAmount();

		final Money taxAmtInfo = Money.of(taxAmt, orderLineCurrencyId);

		final CurrencyConversionContext currencyConversionContext = extractShippingPackageCurrencyConversionContext(shippingPackage);

		return moneyService.convertMoneyToCurrency(taxAmtInfo.add(qtyNetPriceFromOrderLine), currencyId, currencyConversionContext);
	}

	private CurrencyConversionContext extractShippingPackageCurrencyConversionContext(@NonNull final I_M_ShippingPackage shippingPackage)
	{
		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(ShipperTransportationId.ofRepoId(shippingPackage.getM_ShipperTransportation_ID()));
		return currencyBL.createCurrencyConversionContext(
				shipperTransportation.getDateDoc().toInstant(),
				(CurrencyConversionTypeId)null,
				ClientId.ofRepoId(shipperTransportation.getAD_Client_ID()),
				OrgId.ofRepoId(shipperTransportation.getAD_Org_ID()));
	}
}
