package de.metas.procurement.base;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import de.metas.adempiere.service.ICalendarDAO;
import de.metas.adempiere.service.IPeriodBL;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.interfaces.I_C_BPartner;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Helper class to create an PMM contract.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class PMMContractBuilder
{
	/**
	 * Create a new builder in order to create a new flatrate term and data entries.
	 *
	 * @return
	 */
	public static PMMContractBuilder newBuilder()
	{
		return new PMMContractBuilder(null);
	}

	/**
	 * Creates a new builder with an existing term.
	 * Use this method if you already have a term and only need the builder to create {@link I_C_Flatrate_DataEntry}s and maybe complete the term.
	 *
	 * If a builder is created this way, then the {@link #build()} method will only create the term's data entries and not change any property of the given <code>term</code>.
	 * Depending on {@link #setComplete(boolean)}, it might however complete the given <code>term</code>.
	 *
	 * @param term
	 * @return
	 */
	public static PMMContractBuilder newBuilder(final I_C_Flatrate_Term term)
	{
		return new PMMContractBuilder(term);

	}

	// services
	private static final Logger logger = LogManager.getLogger(PMMContractBuilder.class);
	private final transient IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final transient IPMMContractsBL pmmContractsBL = Services.get(IPMMContractsBL.class);
	private final transient ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
	private final transient IPeriodBL periodBL = Services.get(IPeriodBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters for the term
	private IContextAware _context;
	private I_C_Flatrate_Conditions _flatrateConditions;
	private I_C_BPartner _bpartner;
	private Timestamp _startDate;
	private Timestamp _endDate;
	private I_PMM_Product _pmmProduct;
	private I_C_UOM _uom;
	private Optional<I_AD_User> _userInCharge;
	private I_C_Currency _currency;
	private boolean _failIfNotCreated = true;
	private boolean _complete = false;

	//
	// Parameters for the dataEntries
	private BigDecimal _flatrateAmtPerUOM;
	private final Map<Date, BigDecimal> _flatrateAmtPerUOMByDay = new HashMap<>();

	private final Map<Date, DailyFlatrateDataEntry> _flatrateDataEntriesByDay = new HashMap<>();

	//
	// Status
	private final AtomicBoolean _processed = new AtomicBoolean(false);

	private final AtomicReference<I_C_Flatrate_Term> _flatrateTermRef = new AtomicReference<>(null);

	private PMMContractBuilder(final I_C_Flatrate_Term term)
	{
		_flatrateTermRef.set(term);
	}

	/**
	 *
	 * @return the term that was created (if this instance was created with {@link #newBuilder()} or the pre-existing term (if this instance was created with {@link #newBuilder(I_C_Flatrate_Term)}).
	 */
	public I_C_Flatrate_Term build()
	{
		//
		// Mark builder as processed
		final boolean alreadyProcessed = _processed.getAndSet(true);
		Check.assume(!alreadyProcessed, "Not already processed");

		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				final I_C_Flatrate_Term flatrateTerm = buildInTrx();
				_flatrateTermRef.set(flatrateTerm);
			}
		});

		return _flatrateTermRef.get();
	}

	private I_C_Flatrate_Term buildInTrx()
	{
		// gh #489 only create a new term if none was given to this builder when it was created.
		final I_C_Flatrate_Term contract;
		if (_flatrateTermRef.get() == null)
		{
			//
			// Create the contract header
			contract = createContractHeader();
			if (contract == null)
			{
				// NOTE: we are not throwing an exception here
				// because, if configured, an exception was already thrown in the method who created the contract
				return null;
			}
		}
		else
		{
			contract = _flatrateTermRef.get();
		}

		//
		// Create the data entries
		for (final I_C_Period period : retrivePeriods(contract))
		{
			createFlatrateDataEntry(contract, period);
		}
		// Validate
		assertDailyFlatrateDataEntriesAreFullyAllocated(contract);

		//
		// Complete the contract if requested
		if (isComplete())
		{
			flatrateBL.complete(contract);
		}

		return contract;
	}

	private I_C_Flatrate_Term createContractHeader()
	{
		final IContextAware context = getContext();
		final boolean completeItOnCreate = false; // don't complete initially, we will do it later if needed
		final I_PMM_Product pmmProduct = getPMM_Product();
		final I_M_Product product = pmmProduct.getM_Product();
		final I_C_BPartner bpartner = getC_BPartner();
		final I_C_Flatrate_Conditions flatrateConditions = getC_Flatrate_Conditions();
		final Timestamp startDate = getStartDate();
		final I_AD_User userInCharge = getAD_User_InCharge();

		final I_C_Flatrate_Term contract;

		try
		{
			contract = InterfaceWrapperHelper.create(flatrateBL.createTerm(context, bpartner, flatrateConditions, startDate, userInCharge, product, completeItOnCreate), I_C_Flatrate_Term.class);
			Check.assumeNotNull(contract, "contract not null"); // shall not happen
		}
		catch (Exception ex)
		{
			// NOTE: an error about why the contract was not created was already logged
			if (isFailIfNotCreated())
			{
				throw new AdempiereException("@NotCreated@ @C_Flatrate_Term_ID@: " + ex.getLocalizedMessage(), ex);
			}

			return null;

		}

		contract.setContractStatus(X_C_Flatrate_Term.CONTRACTSTATUS_Laufend);
		contract.setEndDate(getEndDate());
		contract.setC_Currency(getC_Currency());

		// Product/UOM
		contract.setPMM_Product(pmmProduct);
		contract.setM_Product(product);
		contract.setC_UOM(getC_UOM());

		InterfaceWrapperHelper.save(contract);

		return contract;
	}

	private List<I_C_Period> retrivePeriods(final I_C_Flatrate_Term term)
	{
		final I_C_Calendar calendar = term.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract();

		final Properties ctx = getContext().getCtx();
		final List<I_C_Period> periods = calendarDAO.retrievePeriods(ctx, calendar, term.getStartDate(), term.getEndDate(), ITrx.TRXNAME_None);
		return periods;
	}

	private I_C_Flatrate_DataEntry createFlatrateDataEntry(final I_C_Flatrate_Term term, final I_C_Period period)
	{
		final IContextAware context = getContext();
		final I_C_Flatrate_DataEntry newDataEntry = InterfaceWrapperHelper.newInstance(I_C_Flatrate_DataEntry.class, context);

		newDataEntry.setC_Flatrate_Term(term);
		newDataEntry.setC_Period(period);
		newDataEntry.setM_Product_DataEntry(term.getM_Product());
		newDataEntry.setC_Currency(term.getC_Currency());
		newDataEntry.setC_UOM(term.getC_UOM());
		newDataEntry.setType(I_C_Flatrate_DataEntry.TYPE_Procurement_PeriodBased);

		// gh #549 if there are _flatrateAmtPerUOMByDay entries for 'period' then use the first one
		final java.util.Optional<Entry<Date, BigDecimal>> flatrateAmtPerUOMForMinDate = _flatrateAmtPerUOMByDay
				.entrySet().stream()
				.filter(e -> {
					final Date date = e.getKey();
					final boolean afterStart = date.equals(period.getStartDate()) || date.after(period.getStartDate());
					final boolean beforeEnd = date.equals(period.getEndDate()) || date.before(period.getEndDate());
					return afterStart && beforeEnd;
				})
				.min(Comparator.comparing(Entry::getKey));

		if (flatrateAmtPerUOMForMinDate.isPresent())
		{
			newDataEntry.setFlatrateAmtPerUOM(flatrateAmtPerUOMForMinDate.get().getValue());
		}
		else
		{
			final BigDecimal flatrateAmtPerUOM = getFlatrateAmtPerUOM_OrNull();
			if (flatrateAmtPerUOM != null)
			{
				newDataEntry.setFlatrateAmtPerUOM(flatrateAmtPerUOM);
			}
		}

		final BigDecimal qtyPlanned = allocateQtyPlannedForPeriod(period);
		if (qtyPlanned != null)
		{
			newDataEntry.setQty_Planned(qtyPlanned);
		}

		InterfaceWrapperHelper.save(newDataEntry);
		return newDataEntry;
	}

	private final void assertNotProcessed()
	{
		Check.assume(!_processed.get(), "Not already processed");
	}

	/**
	 * Sets the context to be used within the {@link #build()} method. Giving a ctx to this builder is mandatory, 
	 * unless the builder was created with {@link #newBuilder(I_C_Flatrate_Term)}.
	 * If that case, if this method was omitted, then the given <code>term</code>'s ctx is used instead.
	 *
	 * @param ctx
	 * @return
	 */
	public PMMContractBuilder setCtx(final Properties ctx)
	{
		assertNotProcessed();
		_context = PlainContextAware.createUsingThreadInheritedTransaction(ctx);
		return this;
	}

	private IContextAware getContext()
	{
		if (_context == null && _flatrateTermRef.get() != null)
		{
			// see javadoc of setCtx method
			return InterfaceWrapperHelper.getContextAware(_flatrateTermRef.get());
		}

		Check.assumeNotNull(_context, "context not null");
		return _context;
	}

	public PMMContractBuilder setC_Flatrate_Conditions(final I_C_Flatrate_Conditions flatrateConditions)
	{
		assertNotProcessed();
		_flatrateConditions = flatrateConditions;
		return this;
	}

	private I_C_Flatrate_Conditions getC_Flatrate_Conditions()
	{
		Check.assumeNotNull(_flatrateConditions, "flatrateConditions not null");
		return _flatrateConditions;
	}

	public PMMContractBuilder setC_BPartner(final org.compiere.model.I_C_BPartner bpartner)
	{
		assertNotProcessed();
		_bpartner = InterfaceWrapperHelper.create(bpartner, I_C_BPartner.class);
		return this;
	}

	private I_C_BPartner getC_BPartner()
	{
		Check.assumeNotNull(_bpartner, "bpartner not null");
		return _bpartner;
	}

	public PMMContractBuilder setStartDate(final Timestamp startDate)
	{
		assertNotProcessed();
		Check.assumeNotNull(startDate, "startDate not null");
		_startDate = TimeUtil.trunc(startDate, TimeUtil.TRUNC_DAY);
		return this;
	}

	private Timestamp getStartDate()
	{
		Check.assumeNotNull(_startDate, "startDate not null");
		return _startDate;
	}

	public PMMContractBuilder setEndDate(final Timestamp endDate)
	{
		assertNotProcessed();
		Check.assumeNotNull(endDate, "endDate not null");
		_endDate = TimeUtil.trunc(endDate, TimeUtil.TRUNC_DAY);;
		return this;
	}

	private Timestamp getEndDate()
	{
		Check.assumeNotNull(_endDate, "endDate not null");
		return _endDate;
	}

	public PMMContractBuilder setPMM_Product(final I_PMM_Product pmmProduct)
	{
		assertNotProcessed();
		_pmmProduct = pmmProduct;
		return this;
	}

	private I_PMM_Product getPMM_Product()
	{
		Check.assumeNotNull(_pmmProduct, "pmmProduct not null");
		return _pmmProduct;
	}

	public PMMContractBuilder setC_UOM(final I_C_UOM uom)
	{
		assertNotProcessed();
		_uom = uom;
		return this;
	}

	private I_C_UOM getC_UOM()
	{
		Check.assumeNotNull(_uom, "uom not null");
		return _uom;
	}

	public PMMContractBuilder setAD_User_InCharge(final I_AD_User userInCharge)
	{
		assertNotProcessed();
		_userInCharge = Optional.fromNullable(userInCharge);
		return this;
	}

	private I_AD_User getAD_User_InCharge()
	{
		// Use default user in charge if no user in charge was specified
		if (_userInCharge == null)
		{
			final I_AD_User defaultUserInCharge = pmmContractsBL.getDefaultContractUserInChargeOrNull(getContext().getCtx());
			_userInCharge = Optional.fromNullable(defaultUserInCharge);
		}
		return _userInCharge.orNull();
	}

	public PMMContractBuilder setC_Currency(final I_C_Currency currency)
	{
		assertNotProcessed();
		_currency = currency;
		return this;
	}

	private I_C_Currency getC_Currency()
	{
		Check.assumeNotNull(_currency, "_currency not null");
		return _currency;
	}

	public PMMContractBuilder setFailIfNotCreated(final boolean failIfNotCreated)
	{
		_failIfNotCreated = failIfNotCreated;
		return this;
	}

	private boolean isFailIfNotCreated()
	{
		return _failIfNotCreated;
	}

	/**
	 * Sets if we shall complete the contract. 
	 * This method is relevant also if this builder was created with {@link #newBuilder(I_C_Flatrate_Term)}.
	 */
	public PMMContractBuilder setComplete(final boolean complete)
	{
		_complete = complete;
		return this;
	}

	private boolean isComplete()
	{
		return _complete;
	}

	/**
	 * Set one price that will be applied to all {@link I_C_Flatrate_DataEntry} that this builder shall create.
	 *
	 * @param flatrateAmtPerUOM
	 * @return
	 */
	public final PMMContractBuilder setFlatrateAmtPerUOM(final BigDecimal flatrateAmtPerUOM)
	{
		assertNotProcessed();
		_flatrateAmtPerUOM = flatrateAmtPerUOM;
		return this;
	}

	/**
	 * Set a price for the given date. When the {@link I_C_Flatrate_DataEntry}s are created according to the term's calendar's {@link I_C_Period}s, then for each period,
	 * the <code>flatrateAmtPerUOM</code> that was set for the earliest date (of course within the period's <code>StartDate</code> and <code>EndDate</code> will be used).
	 * If no such date was added, then the value set via {@link #setFlatrateAmtPerUOM(BigDecimal)} will be used.
	 *
	 * @param date
	 * @param flatrateAmtPerUOM
	 * @return
	 */
	public PMMContractBuilder setFlatrateAmtPerUOM(final Date date, final BigDecimal flatrateAmtPerUOM)
	{
		_flatrateAmtPerUOMByDay.put(date, flatrateAmtPerUOM);
		return this;
	}

	private BigDecimal getFlatrateAmtPerUOM_OrNull()
	{
		return _flatrateAmtPerUOM;
	}

	public PMMContractBuilder addQtyPlanned(final Date date, final BigDecimal qtyPlannedToAdd)
	{
		assertNotProcessed();

		if (qtyPlannedToAdd == null || qtyPlannedToAdd.signum() == 0)
		{
			return this;
		}

		if (qtyPlannedToAdd.signum() < 0)
		{
			logger.warn("Skip adding negative QtyPlanned={} for {}", qtyPlannedToAdd, date);
			return this;
		}

		Check.assumeNotNull(date, "date not null");
		final Timestamp day = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);

		DailyFlatrateDataEntry entry = _flatrateDataEntriesByDay.get(day);
		if (entry == null)
		{
			entry = DailyFlatrateDataEntry.of(day);
			_flatrateDataEntriesByDay.put(day, entry);
		}

		entry.addQtyPlanned(qtyPlannedToAdd);

		return this;
	}

	private BigDecimal allocateQtyPlannedForPeriod(final I_C_Period period)
	{
		BigDecimal qtySum = null;

		for (final DailyFlatrateDataEntry entry : _flatrateDataEntriesByDay.values())
		{
			final Date day = entry.getDay();
			if (!periodBL.isInPeriod(period, day))
			{
				continue;
			}

			final BigDecimal qty = entry.allocateQtyPlanned();
			if (qtySum == null)
			{
				qtySum = qty;
			}
			else
			{
				qtySum = qtySum.add(qty);
			}
		}

		return qtySum;
	}

	private void assertDailyFlatrateDataEntriesAreFullyAllocated(final I_C_Flatrate_Term contract)
	{
		for (final DailyFlatrateDataEntry entry : _flatrateDataEntriesByDay.values())
		{
			if (!entry.isFullyAllocated())
			{
				throw new AdempiereException("Daily entry shall be fully allocated: " + entry
						+ "\n Contract period: " + contract.getStartDate() + "->" + contract.getEndDate());
			}
		}
	}

	private static final class DailyFlatrateDataEntry
	{
		public static final DailyFlatrateDataEntry of(final Date day)
		{
			return new DailyFlatrateDataEntry(day);
		}

		private final Date day;
		private BigDecimal qtyPlanned = BigDecimal.ZERO;
		private BigDecimal qtyPlannedAllocated = BigDecimal.ZERO;

		private DailyFlatrateDataEntry(final Date day)
		{
			super();
			Check.assumeNotNull(day, "day not null");
			this.day = day;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("day", day)
					.add("qtyPlanned", qtyPlanned)
					.add("qtyPlannedAllocated", qtyPlannedAllocated)
					.toString();
		}

		public Date getDay()
		{
			return day;
		}

		public void addQtyPlanned(final BigDecimal qtyPlannedToAdd)
		{
			if (qtyPlannedToAdd == null || qtyPlannedToAdd.signum() == 0)
			{
				return;
			}

			qtyPlanned = qtyPlanned.add(qtyPlannedToAdd);
		}

		public BigDecimal allocateQtyPlanned()
		{
			final BigDecimal qtyPlannedToAllocate = qtyPlanned.subtract(qtyPlannedAllocated);
			qtyPlannedAllocated = qtyPlanned;
			return qtyPlannedToAllocate;
		}

		public boolean isFullyAllocated()
		{
			return qtyPlannedAllocated.compareTo(qtyPlanned) == 0;
		}
	}
}
