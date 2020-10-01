package de.metas.uom.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

public class UOMTestHelper
{
	private final Properties ctx;

	public UOMTestHelper()
	{
		this(Env.getCtx());
	}

	public UOMTestHelper(@NonNull final Properties ctx)
	{
		this.ctx = ctx;
	}

	public ProductId createProduct(final String name, final I_C_UOM uom)
	{
		return createProduct(name, UomId.ofRepoId(uom.getC_UOM_ID()));
	}

	public ProductId createProduct(final String name, final UomId uomId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uomId.getRepoId());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	public I_C_UOM createUOM(final int precision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setStdPrecision(precision);
		return uom;
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision)
	{
		final int costingPrecision = 0;
		return createUOM(name, stdPrecision, costingPrecision);
	}

	public UomId createUOMId(final String name, final int stdPrecision, final int costingPrecision)
	{
		final I_C_UOM uom = createUOM(name, stdPrecision, costingPrecision);
		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecision);

		InterfaceWrapperHelper.save(uom);

		return uom;
	}

	public UomId createUOMId(final String name, final int stdPrecision, final int costingPrecision, final String X12DE355)
	{
		final I_C_UOM uom = createUOM(name, stdPrecision, costingPrecision, X12DE355);
		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecision, final String X12DE355)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setName(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecision);
		uom.setX12DE355(X12DE355);
		uom.setUOMSymbol(X12DE355);

		InterfaceWrapperHelper.save(uom);

		return uom;
	}

	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		Services.get(IUOMConversionDAO.class).createUOMConversion(request);
	}

	public I_C_UOM getUOMById(@NonNull final UomId uomId)
	{
		return Services.get(IUOMDAO.class).getById(uomId);
	}

}
