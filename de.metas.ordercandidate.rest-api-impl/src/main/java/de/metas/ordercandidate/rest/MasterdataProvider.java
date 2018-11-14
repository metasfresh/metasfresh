package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.adempiere.uom.UomId;
import org.adempiere.uom.api.IUOMDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class MasterdataProvider
{
	public static final MasterdataProvider createInstance(final Properties ctx)
	{
		return new MasterdataProvider(ctx);
	}

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsBL = Services.get(IProductBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final OrgId defaultOrgId;

	private final ProductCategoryId defaultProductCategoryId = ProductCategoryId.ofRepoId(1000000); // TODO

	private final Map<String, OrgId> orgIdsByCode = new HashMap<>();

	private final PermissionService permissionService;

	@Getter
	private final BPartnerMasterDataProvider bPartnerMasterDataProvider;

	private MasterdataProvider(final Properties ctx)
	{
		this.defaultOrgId = OrgId.optionalOfRepoId(Env.getAD_Org_ID(ctx)).orElse(OrgId.ANY);
		this.permissionService = PermissionService.of(ctx);
		this.bPartnerMasterDataProvider = BPartnerMasterDataProvider.of(ctx, permissionService);
	}

	public void assertCanCreateNewOLCand(final OrgId orgId)
	{
		permissionService.assertCanCreateOrUpdateRecord(orgId, I_C_OLCand.class);
	}

	public ProductId getCreateProductId(@NonNull final JsonProductInfo json, final OrgId orgId)
	{
		Context context = Context.ofOrg(orgId);
		final ProductId existingProductId;
		if (Check.isEmpty(json.getCode(), true))
		{
			existingProductId = null;
		}
		else
		{
			existingProductId = productsRepo.retrieveProductIdByValue(json.getCode());

		}

		final I_M_Product productRecord;
		if (existingProductId != null)
		{
			productRecord = productsRepo.retrieveProductByValue(json.getCode());
		}
		else
		{
			productRecord = newInstanceOutOfTrx(I_M_Product.class);
			productRecord.setAD_Org_ID(context.getOrgId().getRepoId());
			productRecord.setValue(json.getCode());
		}

		try
		{
			productRecord.setName(json.getName());
			final String productType;
			switch (json.getType())
			{
				case SERVICE:
					productType = X_M_Product.PRODUCTTYPE_Service;
					break;
				case ITEM:
					productType = X_M_Product.PRODUCTTYPE_Item;
					break;
				default:
					Check.fail("Unexpected type={}; jsonProductInfo={}", json.getType(), json);
					productType = null;
					break;
			}

			productRecord.setM_Product_Category_ID(defaultProductCategoryId.getRepoId());

			productRecord.setProductType(productType);

			final UomId uomId = uomsRepo.getUomIdByX12DE355(json.getUomCode());
			productRecord.setC_UOM_ID(UomId.toRepoId(uomId));

			save(productRecord);
		}
		catch (final PermissionNotGrantedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating/updating record for " + json, ex);
		}

		return ProductId.ofRepoId(productRecord.getM_Product_ID());
	}

	public UomId getProductUOMId(
			@NonNull final ProductId productId,
			@Nullable final String uomCode)
	{
		if (!Check.isEmpty(uomCode, true))
		{
			return uomsRepo.getUomIdByX12DE355(uomCode);
		}
		else
		{
			return productsBL.getStockingUOMId(productId);
		}
	}

	public PricingSystemId getPricingSystemIdByValue(final String pricingSystemCode)
	{
		if (Check.isEmpty(pricingSystemCode, true))
		{
			return null;
		}

		return priceListsRepo.getPricingSystemIdByValue(pricingSystemCode);
	}

	public OrgId getCreateOrgId(@Nullable final JsonOrganization json)
	{
		if (json == null)
		{
			return defaultOrgId;
		}

		return orgIdsByCode.compute(json.getCode(), (code, existingOrgId) -> createOrUpdateOrgId(json, existingOrgId));
	}

	private OrgId createOrUpdateOrgId(
			final JsonOrganization json,
			@Nullable OrgId existingOrgId)
	{
		if (existingOrgId == null)
		{
			final String code = json.getCode();
			if (Check.isEmpty(code, true))
			{
				throw new AdempiereException("Organization code shall be set: " + json);
			}

			existingOrgId = orgsRepo.getOrgIdByValue(code).orElse(null);
		}

		final I_AD_Org orgRecord;
		if (existingOrgId != null)
		{
			orgRecord = orgsRepo.getById(existingOrgId);
		}
		else
		{
			orgRecord = newInstance(I_AD_Org.class);
		}

		try
		{
			updateOrgRecord(orgRecord, json);
			permissionService.assertCanCreateOrUpdate(orgRecord);
			orgsRepo.save(orgRecord);
		}
		catch (final PermissionNotGrantedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed creating/updating record for " + json, ex);
		}

		final OrgId orgId = OrgId.ofRepoId(orgRecord.getAD_Org_ID());
		if (json.getBpartner() != null)
		{
			bPartnerMasterDataProvider
					.getCreateOrgBPartnerInfo(
							json.getBpartner(),
							orgId);
		}

		return orgId;
	}

	private void updateOrgRecord(@NonNull final I_AD_Org orgRecord, @NonNull final JsonOrganization json)
	{
		orgRecord.setValue(json.getCode());
		orgRecord.setName(json.getName());
	}

	public JsonOrganization getJsonOrganizationById(final int orgId)
	{
		final I_AD_Org orgRecord = orgsRepo.retrieveOrg(orgId);
		if (orgRecord == null)
		{
			return null;
		}

		return JsonOrganization.builder()
				.code(orgRecord.getValue())
				.name(orgRecord.getName())
				.build();
	}

	public DocTypeId getDocTypeId(
			@NonNull final JsonDocTypeInfo invoiceDocType,
			@NonNull final OrgId orgId)
	{
		final String docSubType = Util.firstNotEmptyTrimmed(
				invoiceDocType.getDocSubType(),
				DocTypeQuery.DOCSUBTYPE_NONE);

		final I_AD_Org orgRecord = orgsRepo.retrieveOrg(orgId.getRepoId());

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(invoiceDocType.getDocBaseType())
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}

	public CurrencyId getCurrencyId(@NonNull final String currencyCode)
	{
		if (Check.isEmpty(currencyCode))
		{
			return null;
		}
		final I_C_Currency currencyRecord = Services
				.get(ICurrencyDAO.class)
				.retrieveCurrencyByISOCode(Env.getCtx(), currencyCode);
		Check.errorIf(currencyRecord == null, "Unable to retrieve a C_Currency for ISO code={}", currencyCode);
		return CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID());
	}

}
