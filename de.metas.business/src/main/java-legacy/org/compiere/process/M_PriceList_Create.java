package org.compiere.process;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.DB;

import de.metas.process.JavaProcess;

/**
 * Create product prices:
 * <ul>
 * <li>copy them from {@link I_M_PriceList_Version#getM_Pricelist_Version_Base()}
 * <li>apply the discount schema: {@link I_M_PriceList_Version#getM_DiscountSchema()}
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class M_PriceList_Create extends JavaProcess
{
	// Services
	private final transient ISessionBL sessionBL = Services.get(ISessionBL.class);
	private final transient IPriceListBL priceListBL = Services.get(IPriceListBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	// State
	private I_M_PriceList_Version _targetPriceListVersion = null; // lazy

	@Override
	protected String doIt() throws Exception
	{
		// Disabling change log creation because we might create and then update a huge amount of records.
		// To avoid this huge performance issue we are disabling for this thread (08125)
		sessionBL.setDisableChangeLogsForThread(true);

		try
		{
			DB.executeFunctionCallEx( //
					ITrx.TRXNAME_ThreadInherited //
					, "select M_PriceList_Version_CopyFromBase(p_M_PriceList_Version_ID:=?, p_AD_User_ID:=?)" //
					, new Object[] { getTargetPriceListVersion_ID(), getAD_User_ID() } //
			);
			
			cloneASIs();

			// FIXME !!!
			// priceListBL.finishPlvCreation(
			// PlainContextAware.newWithThreadInheritedTrx(getCtx()),
			// getTargetPriceListVersion(),
			// discountSchemaLine,
			// getAD_PInstance_ID());
		}
		finally
		{
			sessionBL.setDisableChangeLogsForThread(false);
		}

		return MSG_OK;
	}

	private void cloneASIs()
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, getTargetPriceListVersion_ID())
				.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, true)
				.create()
				.iterateAndStream()
				.forEach(this::cloneASI);
	}

	private void cloneASI(final I_M_ProductPrice productPrice)
	{
		if (!productPrice.isAttributeDependant())
		{
			return;
		}

		// NOTE: we assume the ASI was set when the initial copy function was executed

		final I_M_AttributeSetInstance sourceASI = productPrice.getM_AttributeSetInstance();
		final I_M_AttributeSetInstance targetASI = sourceASI == null ? null : attributeDAO.copy(sourceASI);

		productPrice.setM_AttributeSetInstance(targetASI);
		InterfaceWrapperHelper.save(productPrice);
	}

	private int getTargetPriceListVersion_ID()
	{
		return getRecord_ID();
	}

	private I_M_PriceList_Version getTargetPriceListVersion()
	{
		if (_targetPriceListVersion == null)
		{
			_targetPriceListVersion = getRecord(I_M_PriceList_Version.class);
			Check.assumeNotNull(_targetPriceListVersion, "price list version not null");
		}
		return _targetPriceListVersion;
	}
}
