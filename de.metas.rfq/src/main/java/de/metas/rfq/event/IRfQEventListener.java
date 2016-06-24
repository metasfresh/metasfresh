package de.metas.rfq.event;

import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.rfq
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

public interface IRfQEventListener
{
	//@formatter:off
	void onBeforeComplete(I_C_RfQ rfq);
	void onAfterComplete(I_C_RfQ rfq);
	void onBeforeClose(I_C_RfQ rfq);
	void onAfterClose(I_C_RfQ rfq);
	void onBeforeUnClose(I_C_RfQ rfq);
	void onAfterUnClose(I_C_RfQ rfq);
	//@formatter:on

	//@formatter:off
	void onDraftCreated(I_C_RfQResponse rfqResponse);
	void onBeforeComplete(I_C_RfQResponse rfqResponse);
	void onAfterComplete(I_C_RfQResponse rfqResponse);
	void onBeforeClose(I_C_RfQResponse rfqResponse);
	void onAfterClose(I_C_RfQResponse rfqResponse);
	void onBeforeUnClose(I_C_RfQResponse rfqResponse);
	void onAfterUnClose(I_C_RfQResponse rfqResponse);
	//@formatter:on
}
