package de.metas.materialtracking.ch.lagerkonf.impl;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

=======
import de.metas.document.DocSubType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.ch.lagerkonf.ILagerKonfQualityBasedConfig;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
<<<<<<< HEAD

/**
 * Abstract implementation of {@link ILagerKonfQualityBasedConfig}.
 *
 * It's main purpose is to contain some common methods and common settings.
 *
 * @author tsa
 *
=======
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Abstract implementation of {@link ILagerKonfQualityBasedConfig}.
 * <p>
 * It's main purpose is to contain some common methods and common settings.
 *
 * @author tsa
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public abstract class AbstractQualityBasedConfig implements ILagerKonfQualityBasedConfig
{
	/**
	 * Sort the {@link IQualityInspectionLine} according to customer requirement
<<<<<<< HEAD
	 *
=======
	 * <p>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * See https://drive.google.com/file/d/0B-AaY-YNDnR5bndhaWZxbVp2N3M/edit.
	 */
	// NOTE: public for testing
	public static final List<QualityInspectionLineType> PP_Order_ReportLineTypes = Arrays.asList(
			QualityInspectionLineType.Raw,
			QualityInspectionLineType.Scrap,
			QualityInspectionLineType.ProducedTotal,
			QualityInspectionLineType.ProducedByProducts,
			QualityInspectionLineType.ProducedTotalWithoutByProducts,
			QualityInspectionLineType.ProducedMain,
			QualityInspectionLineType.ProducedCoProducts
<<<<<<< HEAD
			);

	/**
	 * Sort the {@link QualityInvoiceLineGroupType} according to customer requirement
	 *
=======
	);

	/**
	 * Sort the {@link QualityInvoiceLineGroupType} according to customer requirement
	 * <p>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * See https://drive.google.com/file/d/0B-AaY-YNDnR5bndhaWZxbVp2N3M/edit.
	 */
	// NOTE: public for testing
	public static final List<QualityInvoiceLineGroupType> QualityInvoiceLineGroupType_ForInvoicing = Arrays.asList(
			QualityInvoiceLineGroupType.Scrap,
			QualityInvoiceLineGroupType.ByProduct,
			QualityInvoiceLineGroupType.ProductionOrder,
			QualityInvoiceLineGroupType.Fee,
			QualityInvoiceLineGroupType.MainProduct,
			QualityInvoiceLineGroupType.CoProduct,
			QualityInvoiceLineGroupType.Withholding
<<<<<<< HEAD
			);
=======
	);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	//
	private final IContextAware _context;
	private DocTypeId _invoiceDocTypeDownPaymentId = null;
	private DocTypeId _invoiceDocTypeFinalSettlementId = null;

	public AbstractQualityBasedConfig(@NonNull final IContextAware context)
	{
		_context = context;
	}

	protected final IContextAware getContext()
	{
		return _context;
	}

	@Override
	public List<QualityInspectionLineType> getPPOrderReportLineTypes()
	{
		return PP_Order_ReportLineTypes;
	}

	@Override
	public String getFeeNameForProducedProduct(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		// TODO: configure some where
		// e.g. for product "Futterkarotten" return "Zusätzliche Sortierkosten"
		//return product.getName();
		return "Zus\u00e4tzliche Sortierkosten";
	}

	@Override
	public List<QualityInvoiceLineGroupType> getQualityInvoiceLineGroupTypes()
	{
		return QualityInvoiceLineGroupType_ForInvoicing;
	}

	@Override
	public int getC_DocTypeInvoice_DownPayment_ID()
	{
		if (_invoiceDocTypeDownPaymentId == null)
		{
			final String docSubType = IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment;
<<<<<<< HEAD
			_invoiceDocTypeDownPaymentId = loadDocType(docSubType);
=======
			_invoiceDocTypeDownPaymentId = loadDocType(DocSubType.ofCode(docSubType));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		return _invoiceDocTypeDownPaymentId.getRepoId();
	}

	@Override
	public int getC_DocTypeInvoice_FinalSettlement_ID()
	{
		if (_invoiceDocTypeFinalSettlementId == null)
		{
			final String docSubType = IMaterialTrackingBL.C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement;
<<<<<<< HEAD
			_invoiceDocTypeFinalSettlementId = loadDocType(docSubType);
=======
			_invoiceDocTypeFinalSettlementId = loadDocType(DocSubType.ofCode(docSubType));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		return _invoiceDocTypeFinalSettlementId.getRepoId();
	}

	/**
	 * Returns true if the scrap percentage treshold is less than 100.
	 */
	@Override
	public boolean isFeeForScrap()
	{
		return getScrapPercentageTreshold().compareTo(new BigDecimal("100")) < 0;
	}

<<<<<<< HEAD
	private DocTypeId loadDocType(final String docSubType)
=======
	private DocTypeId loadDocType(final DocSubType docSubType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final IContextAware context = getContext();

		final Properties ctx = context.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx); // FIXME: not sure if it's ok

		return Services.get(IDocTypeDAO.class).getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_APInvoice)
						.docSubType(docSubType)
						.adClientId(adClientId)
						.adOrgId(adOrgId)
						.build());
	}

}
