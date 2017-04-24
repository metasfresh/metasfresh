package de.metas.ui.web.handlingunits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Instances of this class are created by {@link HUDocumentViewLoader}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class HUDocumentView implements IDocumentView, IHUDocumentView
{
	public static final Builder builder(final WindowId windowId)
	{
		return new Builder(windowId);
	}

	public static final HUDocumentView cast(final IDocumentView document)
	{
		return (HUDocumentView)document;
	}

	private final DocumentPath documentPath;
	private final DocumentId documentId;
	private final HUDocumentViewType type;
	private final boolean processed;

	private final Map<String, Object> values;
	private final int huId;
	private final String code;
	// private final JSONLookupValue huUnitType;
	private final JSONLookupValue huStatus;
	private final String packingInfo;
	private final JSONLookupValue product;
	private final JSONLookupValue uom;
	private final BigDecimal qtyCU;

	private final Supplier<HUDocumentViewAttributes> attributesSupplier;

	private final List<HUDocumentView> includedDocuments;

	private transient String _summary; // lazy

	private HUDocumentView(final Builder builder)
	{
		documentPath = builder.getDocumentPath();

		documentId = documentPath.getDocumentId();
		type = builder.getType();
		processed = builder.isProcessed();

		values = builder.buildValuesMap();
		huId = builder.huId;
		code = builder.code;
		// huUnitType = builder.huUnitType;
		huStatus = builder.huStatus;
		packingInfo = builder.packingInfo;
		product = builder.product;
		uom = builder.uom;
		qtyCU = builder.qtyCU;

		includedDocuments = builder.buildIncludedDocuments();

		final HUDocumentViewAttributesProvider attributesProvider = builder.getAttributesProviderOrNull();
		if (attributesProvider != null)
		{
			final DocumentId attributesKey = DocumentId.of(huId);
			attributesSupplier = () -> attributesProvider.getAttributes(documentId, attributesKey);
		}
		else
		{
			attributesSupplier = null;
		}
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public DocumentId getDocumentId()
	{
		return documentId;
	}

	/**
	 * @return {@link HUDocumentViewType}; never returns null.
	 */
	@Override
	public HUDocumentViewType getType()
	{
		return type;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}

	Object getFieldValueAsJson(final String fieldName)
	{
		return values.get(fieldName);
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		return values;
	}

	@Override
	public boolean hasAttributes()
	{
		return attributesSupplier != null;
	}

	@Override
	public HUDocumentViewAttributes getAttributes() throws EntityNotFoundException
	{
		if (attributesSupplier == null)
		{
			throw new EntityNotFoundException("Document does not support attributes");
		}

		final HUDocumentViewAttributes attributes = attributesSupplier.get();
		if (attributes == null)
		{
			throw new EntityNotFoundException("Document does not support attributes");
		}
		return attributes;
	}

	public Supplier<HUDocumentViewAttributes> getAttributesSupplier()
	{
		return attributesSupplier;
	}

	@Override
	public boolean hasIncludedView()
	{
		return false;
	}

	@Override
	public List<HUDocumentView> getIncludedDocuments()
	{
		return includedDocuments;
	}

	/**
	 *
	 * @return the ID of the wrapped HU or a value {@code <= 0} if there is none.
	 */
	public int getM_HU_ID()
	{
		return huId;
	}

	/**
	 *
	 * @return the wrapped HU or {@code null} if there is none.
	 */
	public I_M_HU getM_HU()
	{
		final int huId = getM_HU_ID();
		if (huId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);
	}

	public String getValue()
	{
		return code;
	}

	private JSONLookupValue getHUStatus()
	{
		return huStatus;
	}

	public String getHUStatusKey()
	{
		final JSONLookupValue jsonHUStatus = getHUStatus();
		return jsonHUStatus == null ? null : jsonHUStatus.getKey();
	}

	public boolean isHUStatusPlanning()
	{
		return X_M_HU.HUSTATUS_Planning.equals(getHUStatusKey());
	}

	public boolean isHUStatusActive()
	{
		return X_M_HU.HUSTATUS_Active.equals(getHUStatusKey());
	}

	public boolean isPureHU()
	{
		return getType().isPureHU();
	}

	public boolean isCU()
	{
		return getType().isCU();
	}

	public boolean isTU()
	{
		return getType() == HUDocumentViewType.TU;
	}

	public boolean isLU()
	{
		return getType() == HUDocumentViewType.LU;
	}

	public String getSummary()
	{
		if (_summary == null)
		{
			_summary = buildSummary();
		}
		return _summary;
	}

	private String buildSummary()
	{
		final StringBuilder summary = new StringBuilder();
		final String value = getValue();
		if (!Check.isEmpty(value, true))
		{
			summary.append(value);
		}

		final String packingInfo = getPackingInfo();
		if (!Check.isEmpty(packingInfo, true))
		{
			if (summary.length() > 0)
			{
				summary.append(" ");
			}
			summary.append(packingInfo);
		}

		return summary.toString();
	}

	public JSONLookupValue getProduct()
	{
		return product;
	}

	public int getM_Product_ID()
	{
		final JSONLookupValue productLV = getProduct();
		return productLV == null ? -1 : productLV.getKeyAsInt();
	}

	public String getM_Product_DisplayName()
	{
		final JSONLookupValue productLV = getProduct();
		return productLV == null ? null : productLV.getName();
	}

	public I_M_Product getM_Product()
	{
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
	}

	public String getPackingInfo()
	{
		return packingInfo;
	}

	public JSONLookupValue getUOM()
	{
		return uom;
	}

	/**
	 *
	 * @return the ID of the wrapped UOM or {@code -1} if there is none.
	 */
	public int getC_UOM_ID()
	{
		final JSONLookupValue uomLV = getUOM();
		return uomLV == null ? -1 : uomLV.getKeyAsInt();
	}

	/**
	 *
	 * @return the wrapped UOM or {@code null} if there is none.
	 */
	public I_C_UOM getC_UOM()
	{
		final int uomId = getC_UOM_ID();
		if (uomId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(Env.getCtx(), uomId, I_C_UOM.class, ITrx.TRXNAME_None);
	}

	/**
	 * The CU qty of this line. Generally {@code null}, unless this line represents exactly one {@link IHUProductStorage}.
	 */
	public BigDecimal getQtyCU()
	{
		return qtyCU;
	}

	public LookupValue toLookupValue()
	{
		return IntegerLookupValue.of(getM_HU_ID(), getSummary());
	}

	public String getBarcode()
	{
		if (!isPureHU())
		{
			return null;
		}

		//
		// Try fetching SSCC first!
		final String sscc18 = getAttributes().getSSCC18().orElse(null);
		if (sscc18 != null)
		{
			return sscc18;
		}

		//
		// Use HU's code (i.e. M_HU.Value)
		final String huCode = getValue();
		return huCode;
	}

	public boolean matchesBarcode(final String barcodeToMatch)
	{
		if (Check.isEmpty(barcodeToMatch, true))
		{
			throw new IllegalArgumentException("Invalid barcode: " + barcodeToMatch);
		}

		final String barcodeToMatchNormalized = barcodeToMatch.trim();

		final String huBarcode = getBarcode();
		if (huBarcode == null)
		{
			return false;
		}

		return Objects.equals(huBarcode, barcodeToMatchNormalized);
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final WindowId windowId;
		private DocumentId _documentId;
		private HUDocumentViewType type;
		private Boolean processed;

		private Integer huId;
		private String code;
		private JSONLookupValue huUnitType;
		private JSONLookupValue huStatus;
		private String packingInfo;
		private JSONLookupValue product;
		private JSONLookupValue uom;
		private BigDecimal qtyCU;

		private List<HUDocumentView> includedDocuments = null;

		private HUDocumentViewAttributesProvider attributesProvider;

		private Builder(@NonNull final WindowId windowId)
		{
			this.windowId = windowId;
		}

		public HUDocumentView build()
		{
			return new HUDocumentView(this);
		}

		private ImmutableMap<String, Object> buildValuesMap()
		{
			final ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_M_HU_ID, huId);
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_Value, code);
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_HU_UnitType, huUnitType);
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_HUStatus, huStatus);
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_PackingInfo, packingInfo);

			putIfNotNull(map, IHUDocumentView.COLUMNNAME_M_Product_ID, product);
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_C_UOM_ID, uom);
			putIfNotNull(map, IHUDocumentView.COLUMNNAME_QtyCU, qtyCU);

			return map.build();
		}

		private static final void putIfNotNull(final ImmutableMap.Builder<String, Object> map, final String name, final Object value)
		{
			if (value == null)
			{
				return;
			}
			map.put(name, value);
		}

		private DocumentPath getDocumentPath()
		{
			final DocumentId documentId = getDocumentId();
			return DocumentPath.rootDocumentPath(windowId, documentId);
		}

		public Builder setDocumentId(final DocumentId documentId)
		{
			_documentId = documentId;
			return this;
		}

		/** @return view row ID */
		private DocumentId getDocumentId()
		{
			Check.assumeNotNull(_documentId, "Parameter _documentId is not null");
			return _documentId;
		}

		private HUDocumentViewType getType()
		{
			Check.assumeNotNull(type, "Parameter type is not null");
			return type;
		}

		public Builder setType(final HUDocumentViewType type)
		{
			this.type = type;
			return this;
		}

		public Builder setProcessed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}

		private boolean isProcessed()
		{
			if (processed == null)
			{
				// NOTE: don't take the "Processed" field if any, because in frontend we will end up with a lot of grayed out completed sales orders, for example.
				// return DisplayType.toBoolean(values.getOrDefault("Processed", false));
				return false;
			}
			else
			{
				return processed.booleanValue();
			}
		}

		public Builder setHUId(final Integer huId)
		{
			this.huId = huId;
			return this;
		}

		public Builder setCode(final String code)
		{
			this.code = code;
			return this;
		}

		public Builder setHUUnitType(final JSONLookupValue huUnitType)
		{
			this.huUnitType = huUnitType;
			return this;
		}

		public Builder setHUStatus(final JSONLookupValue huStatus)
		{
			this.huStatus = huStatus;
			return this;
		}

		public Builder setPackingInfo(final String packingInfo)
		{
			this.packingInfo = packingInfo;
			return this;
		}

		public Builder setProduct(final JSONLookupValue product)
		{
			this.product = product;
			return this;
		}

		public Builder setUOM(final JSONLookupValue uom)
		{
			this.uom = uom;
			return this;
		}

		public Builder setQtyCU(final BigDecimal qtyCU)
		{
			this.qtyCU = qtyCU;
			return this;
		}

		private HUDocumentViewAttributesProvider getAttributesProviderOrNull()
		{
			return attributesProvider;
		}

		public Builder setAttributesProvider(@Nullable final HUDocumentViewAttributesProvider attributesProvider)
		{
			this.attributesProvider = attributesProvider;
			return this;
		}

		public Builder addIncludedDocument(final HUDocumentView includedDocument)
		{
			if (includedDocuments == null)
			{
				includedDocuments = new ArrayList<>();
			}

			includedDocuments.add(includedDocument);

			return this;
		}

		private List<HUDocumentView> buildIncludedDocuments()
		{
			if (includedDocuments == null || includedDocuments.isEmpty())
			{
				return ImmutableList.of();
			}

			return ImmutableList.copyOf(includedDocuments);
		}
	}
}
