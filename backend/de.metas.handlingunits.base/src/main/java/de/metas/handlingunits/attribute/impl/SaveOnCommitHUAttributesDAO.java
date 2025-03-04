package de.metas.handlingunits.attribute.impl;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.HUAndPIAttributes;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class SaveOnCommitHUAttributesDAO implements IHUAttributesDAO
{
	private static final String TRX_PROPERTY_SaveDecoupledHUAttributesDAO = SaveDecoupledHUAttributesDAO.class.getName();
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final HUAttributesDAO dbHUAttributesDAO;

	public SaveOnCommitHUAttributesDAO()
	{
		dbHUAttributesDAO = HUAttributesDAO.instance;
	}

	@Nullable
	private SaveDecoupledHUAttributesDAO getDelegateOrNull()
	{
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trx == null || trxManager.isNull(trx))
		{
			return null;
		}

		return trx.getProperty(TRX_PROPERTY_SaveDecoupledHUAttributesDAO);
	}

	private SaveDecoupledHUAttributesDAO getDelegate()
	{
		final String trxName = trxManager.getThreadInheritedTrxName();
		final ITrx trx = trxManager.getTrx(trxName);
		Check.assumeNotNull(trx, "trx not null for trxName={}", trxName);

		//
		// Get an existing storage DAO from transaction.
		// If no storage DAO exists => create a new one
		return trx.getProperty(TRX_PROPERTY_SaveDecoupledHUAttributesDAO, () -> {
			// Create a new attributes storage
			final SaveDecoupledHUAttributesDAO huAttributesDAO = new SaveDecoupledHUAttributesDAO(dbHUAttributesDAO);

			// Listen this transaction for COMMIT events
			// Before committing the transaction, this listener makes sure we are also saving all storages
			trx.getTrxListenerManager()
					.newEventListener(TrxEventTiming.BEFORE_COMMIT)
					.registerHandlingMethod(innerTrx -> {

						// Get and remove the save-decoupled HU Storage DAO
						final SaveDecoupledHUAttributesDAO innerHuAttributesDAO = innerTrx.setProperty(TRX_PROPERTY_SaveDecoupledHUAttributesDAO, null);
						if (innerHuAttributesDAO == null)
						{
							// shall not happen, because this handlerMethod is invoked only once,
							// but silently ignore it
							return;
						}

						// Save everything to database
						innerHuAttributesDAO.flush();
					});

			return huAttributesDAO;
		});
	}

	@Override
	public I_M_HU_Attribute newHUAttribute(final Object contextProvider)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate();
		return delegate.newHUAttribute(contextProvider);
	}

	@Override
	public void save(final I_M_HU_Attribute huAttribute)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate();
		delegate.save(huAttribute);
	}

	@Override
	public void delete(final I_M_HU_Attribute huAttribute)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate();
		delegate.delete(huAttribute);
	}

	@Override
	public List<I_M_HU_Attribute> retrieveAllAttributesNoCache(final Collection<HuId> huIds)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate();
		return delegate.retrieveAllAttributesNoCache(huIds);
	}

	@Override
	public HUAndPIAttributes retrieveAttributesOrdered(final I_M_HU hu)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate();
		return delegate.retrieveAttributesOrdered(hu);
	}

	@Override
	public I_M_HU_Attribute retrieveAttribute(final I_M_HU hu, final AttributeId attributeId)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate();
		return delegate.retrieveAttribute(hu, attributeId);
	}

	@Override
	public void flush()
	{
		final SaveDecoupledHUAttributesDAO attributesDAO = getDelegateOrNull();
		if (attributesDAO != null)
		{
			attributesDAO.flush();
		}
	}
}
