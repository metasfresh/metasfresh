package de.metas.edi.sscc18;

import com.google.common.base.Suppliers;
import de.metas.adempiere.model.I_M_Product;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.api.impl.pack.EDIDesadvPack;
import de.metas.edi.api.impl.pack.EDIDesadvPackRepository;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class PrintableDesadvLineSSCC18Labels implements IPrintableDesadvLineSSCC18Labels
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final I_EDI_DesadvLine desadvLine;

	private final int lineNo;
	private final String productValue;
	private final String productName;

	private final int existingSSCC18sCount;
	private final Supplier<List<EDIDesadvPack>> existingSSCC18s;
	private BigDecimal requiredSSCC18sCount;

	private final TotalQtyCUBreakdownCalculator huQtysCalculator;

	private PrintableDesadvLineSSCC18Labels(@NonNull final Builder builder)
	{
		this.desadvLine = builder.getEDI_DesadvLine();

		this.lineNo = builder.getLineNo();

		this.productValue = builder.getProductValue();
		this.productName = builder.getProductName();

		this.existingSSCC18s = builder.getExistingSSCC18s();
		this.existingSSCC18sCount = this.existingSSCC18s.get().size();
		this.requiredSSCC18sCount = BigDecimal.valueOf(builder.getRequiredSSCC18Count());

		this.huQtysCalculator = builder.getHUQtysCalculator();
	}

	@Override
	public I_EDI_DesadvLine getEDI_DesadvLine()
	{
		return desadvLine;
	}

	@Override
	public Integer getLineNo()
	{
		return lineNo;
	}

	@Override
	public String getProductValue()
	{
		return productValue;
	}

	@Override
	public String getProductName()
	{
		return productName;
	}

	@Override
	public Integer getExistingSSCC18sCount()
	{
		return existingSSCC18sCount;
	}

	@Override
	public List<EDIDesadvPack> getExistingSSCC18s()
	{
		return existingSSCC18s.get();
	}

	@Override
	public BigDecimal getRequiredSSCC18sCount()
	{
		return requiredSSCC18sCount;
	}

	@Override
	public void setRequiredSSCC18sCount(final BigDecimal requiredSSCC18sCount)
	{
		this.requiredSSCC18sCount = requiredSSCC18sCount;
	}

	@Override
	public TotalQtyCUBreakdownCalculator breakdownTotalQtyCUsToLUs()
	{
		return huQtysCalculator.copy();
	}

	public static class Builder
	{
		// services
		private static final transient Logger logger = LogManager.getLogger(PrintableDesadvLineSSCC18Labels.class);
		private final transient IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
		private final transient IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

		private final transient EDIDesadvPackRepository EDIDesadvPackRepository = SpringContextHolder.instance.getBean(EDIDesadvPackRepository.class);

		private I_EDI_DesadvLine _desadvLine;
		private Integer requiredSSCC18Count;

		@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
		private Optional<I_M_ShipmentSchedule> _shipmentSchedule;
		@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
		private Optional<I_M_HU_LUTU_Configuration> _lutuConfiguration;

		TotalQtyCUBreakdownCalculator _huQtysCalculator = null;

		private Builder()
		{
		}

		public PrintableDesadvLineSSCC18Labels build()
		{
			return new PrintableDesadvLineSSCC18Labels(this);
		}

		public Builder setEDI_DesadvLine(final I_EDI_DesadvLine desadvLine)
		{
			this._desadvLine = desadvLine;
			return this;
		}

		/**
		 * @return {@link I_EDI_DesadvLine}; never returns null
		 */
		I_EDI_DesadvLine getEDI_DesadvLine()
		{
			Check.assumeNotNull(_desadvLine, "desadvLine not null");
			return _desadvLine;
		}

		int getLineNo()
		{
			return _desadvLine.getLine();
		}

		String getProductValue()
		{
			final I_M_Product productRecord = loadOutOfTrx(_desadvLine.getM_Product_ID(), I_M_Product.class);
			return productRecord.getValue();
		}

		String getProductName()
		{
			return _desadvLine.getProductDescription();
		}

		Supplier<List<EDIDesadvPack>> getExistingSSCC18s()
		{
			return Suppliers.memoize(() -> EDIDesadvPackRepository.getPacksByEDIDesadvLineId(EDIDesadvLineId.ofRepoId(_desadvLine.getEDI_DesadvLine_ID())));
		}

		public Builder setRequiredSSCC18Count(final Integer requiredSSCC18Count)
		{
			this.requiredSSCC18Count = requiredSSCC18Count;
			return this;
		}

		public int getRequiredSSCC18Count()
		{
			if (requiredSSCC18Count != null)
			{
				return requiredSSCC18Count;
			}
			return getHUQtysCalculator().getAvailableLUs();
		}

		private TotalQtyCUBreakdownCalculator getHUQtysCalculator()
		{
			if (_huQtysCalculator == null)
			{
				try
				{
					_huQtysCalculator = createHUQtysCalculator();
				}
				catch (final Exception e)
				{
					logger.warn("Failed to calculate qtys for " + _desadvLine, e);
					_huQtysCalculator = TotalQtyCUBreakdownCalculator.NULL;
				}
			}
			return _huQtysCalculator;
		}

		private TotalQtyCUBreakdownCalculator createHUQtysCalculator()
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration().orElse(null);
			if (lutuConfiguration == null)
			{
				// NOTE: an warning shall be already logged
				return TotalQtyCUBreakdownCalculator.NULL;
			}

			final I_M_ShipmentSchedule shipmentSchedule = getM_ShipmentSchedule().orElse(null);
			if (shipmentSchedule == null)
			{
				// NOTE: an warning shall be already logged
				return TotalQtyCUBreakdownCalculator.NULL;
			}

			final TotalQtyCUBreakdownCalculator.Builder builder = TotalQtyCUBreakdownCalculator.builder();

			final Quantity qtyCUsTotal = lutuConfigurationFactory.convertQtyToLUTUConfigurationUOM(
					Quantity.of(shipmentSchedule.getQtyOrdered(),
								Services.get(IProductBL.class).getStockUOM(shipmentSchedule.getM_Product_ID())),
					lutuConfiguration);

			builder.setQtyCUsTotal(qtyCUsTotal.toBigDecimal());

			final BigDecimal qtyTUsTotal = shipmentSchedule.getQtyOrdered_TU();
			if (qtyTUsTotal.signum() > 0)
			{
				builder.setQtyTUsTotal(qtyTUsTotal);
			}

			if (lutuConfiguration.isInfiniteQtyCU())
			{
				builder.setStandardQtysAsInfinite();
			}
			else if (lutuConfiguration.isInfiniteQtyTU())
			{
				builder.setStandardQtysAsInfinite();
			}
			else
			{
				builder.setStandardQtyCUsPerTU(lutuConfiguration.getQtyCUsPerTU());
				builder.setStandardQtyTUsPerLU(lutuConfiguration.getQtyTU());
			}

			return builder.build();
		}

		private Optional<I_M_ShipmentSchedule> getM_ShipmentSchedule()
		{
			//noinspection OptionalAssignedToNull
			if (_shipmentSchedule == null)
			{
				final I_EDI_DesadvLine desadvLine = getEDI_DesadvLine();
				final I_M_ShipmentSchedule shipmentSchedule = desadvDAO.retrieveM_ShipmentScheduleOrNull(desadvLine);
				if (shipmentSchedule == null)
				{
					logger.warn("No shipment schedule was found for {}", desadvLine);
				}
				_shipmentSchedule = Optional.ofNullable(shipmentSchedule);
			}
			return _shipmentSchedule;
		}

		private Optional<I_M_HU_LUTU_Configuration> getM_HU_LUTU_Configuration()
		{
			//noinspection OptionalAssignedToNull
			if (_lutuConfiguration == null)
			{
				_lutuConfiguration = retrieveM_HU_LUTU_Configuration();
			}
			return _lutuConfiguration;
		}

		private Optional<I_M_HU_LUTU_Configuration> retrieveM_HU_LUTU_Configuration()
		{
			final I_M_ShipmentSchedule shipmentSchedule = getM_ShipmentSchedule().orElse(null);
			if (shipmentSchedule == null)
			{
				return Optional.empty();
			}

			final I_M_HU_LUTU_Configuration lutuConfiguration = huShipmentScheduleBL.deriveM_HU_LUTU_Configuration(shipmentSchedule);
			if (lutuConfiguration == null) // shall not happen
			{
				logger.warn("No LU/TU configuration found for {}", shipmentSchedule);
				return Optional.empty();
			}
			if (lutuConfigurationFactory.isNoLU(lutuConfiguration))
			{
				logger.warn("No LU PI found for {}", shipmentSchedule);
				return Optional.empty();
			}

			return Optional.of(lutuConfiguration);
		}

	} // Builder
}
