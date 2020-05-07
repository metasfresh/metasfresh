package de.metas.edi.sscc18;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.edi.api.IDesadvDAO;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;

public class PrintableDesadvLineSSCC18Labels implements IPrintableDesadvLineSSCC18Labels
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final I_EDI_DesadvLine desadvLine;

	private final int lineNo;
	private final String productValue;
	private final String productName;

	private int existingSSCC18sCount;
	private final Supplier<List<I_EDI_DesadvLine_SSCC>> existingSSCC18s;
	private BigDecimal requiredSSCC18sCount;

	private TotalQtyCUBreakdownCalculator huQtysCalculator;

	private PrintableDesadvLineSSCC18Labels(final Builder builder)
	{
		super();

		this.desadvLine = builder.getEDI_DesadvLine();

		this.lineNo = builder.getLineNo();

		this.productValue = builder.getProductValue();
		this.productName = builder.getProductName();

		this.existingSSCC18sCount = builder.getExistingSSCC18sCount();
		this.existingSSCC18s = builder.getExistingSSCC18s();
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
	public List<I_EDI_DesadvLine_SSCC> getExistingSSCC18s()
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

		private I_EDI_DesadvLine _desadvLine;
		private Integer existingSSCC18Count;
		private Integer requiredSSCC18Count;

		private Optional<I_M_ShipmentSchedule> _shipmentSchedule = null;
		private Optional<I_M_HU_LUTU_Configuration> _lutuConfiguration = null;

		TotalQtyCUBreakdownCalculator _huQtysCalculator = null;

		private Builder()
		{
			super();
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

		/** @return {@link I_EDI_DesadvLine}; never returns null */
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
			return _desadvLine.getM_Product().getValue();
		}

		String getProductName()
		{
			return _desadvLine.getProductDescription();
		}

		int getExistingSSCC18sCount()
		{
			if (existingSSCC18Count != null)
			{
				return existingSSCC18Count;
			}

			return desadvDAO.retrieveDesadvLineSSCCsCount(_desadvLine);
		}

		Supplier<List<I_EDI_DesadvLine_SSCC>> getExistingSSCC18s()
		{
			return Suppliers.memoize(new Supplier<List<I_EDI_DesadvLine_SSCC>>()
			{

				@Override
				public List<I_EDI_DesadvLine_SSCC> get()
				{
					return desadvDAO.retrieveDesadvLineSSCCs(_desadvLine);
				}
			});
		}

		public Builder setRequiredSSCC18Count(final Integer requiredSSCC18Count)
		{
			this.requiredSSCC18Count = requiredSSCC18Count;
			return this;
		}

		int getRequiredSSCC18Count()
		{
			if (requiredSSCC18Count != null)
			{
				return requiredSSCC18Count;
			}

			return getHUQtysCalculator().getAvailableLUs();
		}

		private final TotalQtyCUBreakdownCalculator getHUQtysCalculator()
		{
			if (_huQtysCalculator == null)
			{
				try
				{
					_huQtysCalculator = createHUQtysCalculator();
				}
				catch (Exception e)
				{
					logger.warn("Failed to calculate qtys for " + _desadvLine, e);
					_huQtysCalculator = TotalQtyCUBreakdownCalculator.NULL;
				}
			}
			return _huQtysCalculator;
		}

		private final TotalQtyCUBreakdownCalculator createHUQtysCalculator()
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = getM_HU_LUTU_Configuration().orNull();
			if (lutuConfiguration == null)
			{
				// NOTE: an warning shall be already logged
				return TotalQtyCUBreakdownCalculator.NULL;
			}

			final I_M_ShipmentSchedule shipmentSchedule = getM_ShipmentSchedule().orNull();
			if (shipmentSchedule == null)
			{
				// NOTE: an warning shall be already logged
				return TotalQtyCUBreakdownCalculator.NULL;
			}

			TotalQtyCUBreakdownCalculator.Builder builder = TotalQtyCUBreakdownCalculator.builder();

			final Quantity qtyCUsTotal = lutuConfigurationFactory.convertQtyToLUTUConfigurationUOM(
					shipmentSchedule.getQtyOrdered(), shipmentSchedule.getC_UOM(),
					lutuConfiguration);
			builder.setQtyCUsTotal(qtyCUsTotal.getQty());

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
				builder.setStandardQtyCUsPerTU(lutuConfiguration.getQtyCU());
				builder.setStandardQtyTUsPerLU(lutuConfiguration.getQtyTU());
			}

			return builder.build();
		}

		private final Optional<I_M_ShipmentSchedule> getM_ShipmentSchedule()
		{
			if (_shipmentSchedule == null)
			{
				final I_EDI_DesadvLine desadvLine = getEDI_DesadvLine();
				final I_M_ShipmentSchedule shipmentSchedule = desadvDAO.retrieveM_ShipmentScheduleOrNull(desadvLine);
				if (shipmentSchedule == null)
				{
					logger.warn("No shipment schedule was found for {}", desadvLine);
				}
				_shipmentSchedule = Optional.fromNullable(shipmentSchedule);
			}
			return _shipmentSchedule;
		}

		private final Optional<I_M_HU_LUTU_Configuration> getM_HU_LUTU_Configuration()
		{
			if (_lutuConfiguration == null)
			{
				_lutuConfiguration = retrieveM_HU_LUTU_Configuration();
			}
			return _lutuConfiguration;
		}

		private final Optional<I_M_HU_LUTU_Configuration> retrieveM_HU_LUTU_Configuration()
		{
			final I_M_ShipmentSchedule shipmentSchedule = getM_ShipmentSchedule().orNull();
			if (shipmentSchedule == null)
			{
				return Optional.absent();
			}

			final I_M_HU_LUTU_Configuration lutuConfiguration = huShipmentScheduleBL.deriveM_HU_LUTU_Configuration(shipmentSchedule);
			if (lutuConfiguration == null) // shall not happen
			{
				logger.warn("No LU/TU configuration found for {}", shipmentSchedule);
				return Optional.absent();
			}
			if (lutuConfigurationFactory.isNoLU(lutuConfiguration))
			{
				logger.warn("No LU PI found for {}", shipmentSchedule);
				return Optional.absent();
			}

			return Optional.of(lutuConfiguration);
		}

	} // Builder
}
