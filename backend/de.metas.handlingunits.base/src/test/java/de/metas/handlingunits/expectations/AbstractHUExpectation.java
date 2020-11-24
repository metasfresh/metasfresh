package de.metas.handlingunits.expectations;

import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.text.annotation.ToStringBuilder;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.impl.HUStorageDAO;
import de.metas.util.Services;

public class AbstractHUExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	// services
	@ToStringBuilder(skip = true)
	protected final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@ToStringBuilder(skip = true)
	protected final IHUStorageDAO huStorageDAO = new HUStorageDAO();

	public AbstractHUExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	/** No parent constructor */
	protected AbstractHUExpectation()
	{
	}

}
