package de.metas.procurement.webui.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.vaadin.data.util.BeanItemContainer;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.event.MFEventBus;
import de.metas.procurement.webui.event.RfqChangedEvent;
import de.metas.procurement.webui.event.UIApplicationEventListenerAdapter;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IRfQService;

/*
 * #%L
 * metasfresh-procurement-webui
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

@SuppressWarnings("serial")
public class RfqHeaderContainer extends BeanItemContainer<RfqHeader>
{
	@Autowired
	@Lazy
	private IRfQService rfqService;
	@Autowired
	private MFEventBus applicationEventBus;

	private final User user;

	public RfqHeaderContainer(final User user)
	{
		super(RfqHeader.class);
		Application.autowire(this);

		this.user = user;

		applicationEventBus.register(new UIApplicationEventListenerAdapter()
		{
			@Override
			public void onRfqChanged(final RfqChangedEvent event)
			{
				notifyRfqChanged(event);
			}
		});
	}

	public void loadAll()
	{
		removeAllItems();

		final List<RfqHeader> activeRfqs = rfqService.getActiveRfqHeaders(user);
		addAll(activeRfqs);
	}

	public void notifyRfqChanged(final RfqChangedEvent event)
	{
		RfqHeader rfqHeader = getRfqHeaderByUuid(event.getRfq_uuid());
		if(rfqHeader == null)
		{
			if(event.isClosed())
			{
				// skip adding it
				return;
			}
			
			// create and add new
			rfqHeader = rfqService.getActiveRfqHeaderById(event.getRfq_id());
			if(rfqHeader != null)
			{
				addBean(rfqHeader);
			}
			
			return;
		}

		if (event.isClosed())
		{
			// TODO: shall we notify the user too?
			removeItem(rfqHeader);
		}		
	}

	private RfqHeader getRfqHeaderByUuid(final String rfq_uuid)
	{
		for (final RfqHeader rfqHeader : getAllItemIds())
		{
			if (Objects.equal(rfq_uuid, rfqHeader.getRfq_uuid()))
			{
				return rfqHeader;
			}
		}
		
		return null;
	}

}
