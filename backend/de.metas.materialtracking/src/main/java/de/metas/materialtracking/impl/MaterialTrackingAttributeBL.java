package de.metas.materialtracking.impl;

import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class MaterialTrackingAttributeBL implements IMaterialTrackingAttributeBL
{
	private static final AttributeCode M_Attribute_Value_MaterialTracking = AttributeCode.ofString("M_Material_Tracking_ID");

	@Override
	public Optional<AttributeId> getMaterialTrackingAttributeId()
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final AttributeId attributeId = attributeDAO.retrieveAttributeIdByValueOrNull(M_Attribute_Value_MaterialTracking);
		return Optional.ofNullable(attributeId);
	}

	private AttributeId getMaterialTrackingAttributeIdOrFail()
	{
		return getMaterialTrackingAttributeId()
				.orElseThrow(() -> new AdempiereException("M_Attribute record with Value=" + M_Attribute_Value_MaterialTracking + " is missing or deactivated"));
	}

	@Override
	public I_M_Attribute getMaterialTrackingAttribute()
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final AttributeId attributeId = getMaterialTrackingAttributeIdOrFail();
		return attributeDAO.getAttributeById(attributeId);
	}

	@Override
	public void createOrUpdateMaterialTrackingAttributeValue(@NonNull final I_M_Material_Tracking materialTracking)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final String name = buildName(materialTracking);
		final String value;
		if (materialTracking.getM_Material_Tracking_ID() <= 0)
		{
			// In case it's a new material tracking use the build name for it because value will always by "0" and we could have unique index violations.
			// Please note that there is a model interceptor which is updating the value after new.
			// (see M_Material_Tracking.updateMaterialTrackingAttributeValue_Value(I_M_Material_Tracking))
			value = name;
		}
		else
		{
			value = getMaterialTrackingIdStr(materialTracking);
		}
		final String description = materialTracking.getDescription();
		final boolean isActive = materialTracking.isActive() && !materialTracking.isProcessed();

		//
		// If the attribute value was not already created, create it now
		AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(materialTracking.getM_AttributeValue_ID());
		if (attributeValueId == null)
		{
			final AttributeId attributeId = getMaterialTrackingAttributeIdOrFail();
			final AttributeListValue attributeValue = attributesRepo.createAttributeValue(AttributeListValueCreateRequest.builder()
					.attributeId(attributeId)
					.value(value)
					.name(name)
					.description(description)
					.active(isActive)
					.build());
			attributeValueId = attributeValue.getId();
		}
		else
		{
			final AttributeListValue attributeValue = attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
					.id(attributeValueId)
					.value(Optional.ofNullable(value))
					.name(name)
					.description(Optional.ofNullable(description))
					.active(isActive)
					.build());
			attributeValueId = attributeValue.getId();
		}

		materialTracking.setM_AttributeValue_ID(attributeValueId.getRepoId());
	}

	@Override
	public final String getMaterialTrackingIdStr(final I_M_Material_Tracking materialTracking)
	{
		final int materialTrackingId = materialTracking.getM_Material_Tracking_ID();
		final String materialTrackingIdStr = String.valueOf(materialTrackingId);
		return materialTrackingIdStr;
	}

	private final int getMaterialTrackingIdFromMaterialTrackingIdStr(final String materialTrackingIdStr)
	{
		if (Check.isEmpty(materialTrackingIdStr, true))
		{
			return -1;
		}

		final int materialTrackingId = Integer.parseInt(materialTrackingIdStr.trim());
		return materialTrackingId;
	}

	/**
	 * Build a descriptive name for given material tracking
	 *
	 * @param materialTracking
	 * @return
	 */
	private final String buildName(final I_M_Material_Tracking materialTracking)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final StringBuilder sb = new StringBuilder();

		// ID
		// sb.append(materialTracking.getM_Material_Tracking_ID());

		// Lot
		sb.append(materialTracking.getLot());

		// Vendor/BPartner
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(materialTracking.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			final String bpartnerName = Services.get(IBPartnerBL.class).getBPartnerName(bpartnerId);
			sb.append("_");
			sb.append(bpartnerName);
		}

		// Tracked product
		final ProductId productId = ProductId.ofRepoIdOrNull(materialTracking.getM_Product_ID());
		if (productId != null)
		{
			final String productName = Services.get(IProductBL.class).getProductName(productId);
			sb.append("_");
			sb.append(productName);
		}

		return sb.toString();
	}

	private I_M_AttributeInstance getMaterialTrackingAttributeInstanceOrNull(
			@NonNull final AttributeSetInstanceId asiId,
			final boolean createIfNotFound)
	{
		if (asiId.isNone())
		{
			return null;
		}

		//
		// Get Material Tracking I_M_Attribute's Id, if it exists
		final Optional<AttributeId> materialTrackingAttributeId = getMaterialTrackingAttributeId();
		if (!materialTrackingAttributeId.isPresent())
		{
			return null;
		}

		//
		// Retrieve Material Tracking Attribute Instance (from ASI)
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final I_M_AttributeInstance materialTrackingAttributeInstance = attributeDAO.retrieveAttributeInstance(asiId, materialTrackingAttributeId.get());
		if (materialTrackingAttributeInstance != null)
		{
			return materialTrackingAttributeInstance;
		}

		if (!createIfNotFound)
		{
			return null;
		}

		//
		// Create a new one
		final I_M_AttributeInstance materialTrackingAttributeInstanceNew = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class);
		materialTrackingAttributeInstanceNew.setM_AttributeSetInstance_ID(asiId.getRepoId());
		materialTrackingAttributeInstanceNew.setM_Attribute_ID(materialTrackingAttributeId.get().getRepoId());
		// NOTE: don't save it

		return materialTrackingAttributeInstanceNew;
	}

	private AttributeListValue getMaterialTrackingAttributeValue(final I_M_Material_Tracking materialTracking)
	{
		if (materialTracking == null)
		{
			return null;
		}

		final AttributeId attributeId = getMaterialTrackingAttributeIdOrFail();
		final AttributeValueId attributeValueId = AttributeValueId.ofRepoId(materialTracking.getM_AttributeValue_ID());

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final AttributeListValue materialTrackingAttributeValue = attributesRepo.retrieveAttributeValueOrNull(attributeId, attributeValueId);
		if (materialTrackingAttributeValue == null)
		{
			throw new AdempiereException("@NotFound@ @M_AttributeValue_ID@"
					+ "\n@M_Material_Tracking_ID@: " + materialTracking);
		}

		return materialTrackingAttributeValue;
	}

	@Override
	public void createOrUpdateMaterialTrackingASI(final Object documentLine,
			final I_M_Material_Tracking materialTracking)
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final I_M_AttributeSetInstance documentLineASI;
		final IAttributeSetInstanceAware documentLineASIAware = attributeSetInstanceAwareFactoryService.createOrNull(documentLine);
		Check.assumeNotNull(documentLineASIAware, "IAttributeSetInstanceAwareFactoryService.createOrNull() does not return null for {}", documentLine);

		if (documentLineASIAware.getM_AttributeSetInstance_ID() > 0)
		{
			documentLineASI = attributeDAO.copy(documentLineASIAware.getM_AttributeSetInstance());
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(documentLineASIAware.getM_Product_ID());
			documentLineASI = attributeSetInstanceBL.createASI(productId);
		}

		setM_Material_Tracking(documentLineASI, materialTracking); // update the new ASI
		documentLineASIAware.setM_AttributeSetInstance(documentLineASI); // for inOutLines, this also causes M_InOutLine.M_Material_Tracking_ID to be updated
	}

	@Override
	public void setM_Material_Tracking(final I_M_AttributeSetInstance asi, final I_M_Material_Tracking materialTracking)
	{
		if (asi == null || asi.getM_AttributeSetInstance_ID() <= 0)
		{
			if (materialTracking == null)
			{
				return;
			}

			// When material tracking is specified, we need ASI to be not null
			Check.assumeNotNull(asi, "asi not null");
		}

		//
		// Retrieve Material Tracking Attribute Instance (from ASI)
		final boolean createAttributeInstanceIfNotFound = materialTracking != null;
		final I_M_AttributeInstance materialTrackingAttributeInstance = getMaterialTrackingAttributeInstanceOrNull(
				AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID()),
				createAttributeInstanceIfNotFound);
		if (materialTrackingAttributeInstance == null)
		{
			return;
		}

		//
		// Get corresponding material tracking attribute value
		final AttributeListValue materialTrackingAttributeValue = getMaterialTrackingAttributeValue(materialTracking);

		//
		// Update Attribute Instance
		materialTrackingAttributeInstance.setValue(materialTrackingAttributeValue == null ? null : materialTrackingAttributeValue.getValue());
		materialTrackingAttributeInstance.setM_AttributeValue_ID(materialTrackingAttributeValue.getId().getRepoId());

		InterfaceWrapperHelper.save(materialTrackingAttributeInstance);
	}

	@Override
	public I_M_Material_Tracking getMaterialTrackingOrNull(@Nullable final AttributeSetInstanceId asiId)
	{
		if (asiId == null || asiId.isNone())
		{
			return null;
		}

		final I_M_AttributeInstance materialTrackingAttributeInstance = getMaterialTrackingAttributeInstanceOrNull(asiId, false); // createIfNotFound=false
		if (materialTrackingAttributeInstance == null)
		{
			return null;
		}

		final AttributeValueId materialTrackingAttributeValueId = AttributeValueId.ofRepoIdOrNull(materialTrackingAttributeInstance.getM_AttributeValue_ID());
		if (materialTrackingAttributeValueId == null)
		{
			return null;
		}

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		return materialTrackingDAO.retrieveMaterialTrackingByAttributeValue(materialTrackingAttributeValueId);
	}

	@Override
	public I_M_Material_Tracking getMaterialTracking(final IContextAware context, final IAttributeSet attributeSet)
	{
		final int materialTrackingId = getMaterialTrackingId(context, attributeSet);
		if (materialTrackingId <= 0)
		{
			return null;
		}

		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.create(context.getCtx(), materialTrackingId, I_M_Material_Tracking.class, context.getTrxName());
		Check.assume(materialTracking != null && materialTracking.getM_Material_Tracking_ID() == materialTrackingId,
				"Material tracking record shall exist for ID={}", materialTrackingId);

		return materialTracking;
	}

	@Override
	public boolean isMaterialTrackingSet(final IContextAware context, final IAttributeSet attributeSet)
	{
		final int materialTrackingId = getMaterialTrackingId(context, attributeSet);
		return materialTrackingId > 0;
	}

	private final int getMaterialTrackingId(final IContextAware context, final IAttributeSet attributeSet)
	{
		if (!attributeSet.hasAttribute(M_Attribute_Value_MaterialTracking))
		{
			return -1;
		}
		final Object materialTrackingIdObj = attributeSet.getValue(M_Attribute_Value_MaterialTracking);
		if (materialTrackingIdObj == null)
		{
			return -1;
		}

		final String materialTrackingIdStr = materialTrackingIdObj.toString();
		final int materialTrackingId = getMaterialTrackingIdFromMaterialTrackingIdStr(materialTrackingIdStr);
		return materialTrackingId;
	}

	@Override
	public boolean hasMaterialTrackingAttribute(@NonNull final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return false;
		}

		final Optional<AttributeId> materialTrackingAttributeId = getMaterialTrackingAttributeId();
		if (!materialTrackingAttributeId.isPresent())
		{
			return false;
		}

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final I_M_AttributeInstance materialTrackingAttributeInstance = attributeDAO.retrieveAttributeInstance(asiId, materialTrackingAttributeId.get());

		return materialTrackingAttributeInstance != null;
	}
}
